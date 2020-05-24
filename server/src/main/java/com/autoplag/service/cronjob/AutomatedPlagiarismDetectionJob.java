package com.autoplag.service.cronjob;

import com.autoplag.config.constant.Detection;
import com.autoplag.persistence.domain.PlagDetectionResult;
import com.autoplag.persistence.domain.TaskGroup;
import com.autoplag.persistence.domain.type.PlagDetectionStatus;
import com.autoplag.service.PlagDetectionService;
import com.autoplag.service.TaskGroupService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Slf4j
public class AutomatedPlagiarismDetectionJob extends Thread {
    private final List<TaskGroup> taskGroups;
    private final PlagDetectionService plagDetectionService;
    private final TaskGroupService taskGroupService;

    public AutomatedPlagiarismDetectionJob(TaskGroupService taskGroupService,
                                           PlagDetectionService plagiarismDetectionService,
                                           List<TaskGroup> taskGroups) {
        super("AutomatedPlagiarismDetectionJob with taskGroups.size=" + taskGroups.size());
        this.taskGroupService = taskGroupService;
        this.plagDetectionService = plagiarismDetectionService;
        this.taskGroups = taskGroups;
    }

    @Override
    public void run() {
        log.debug("Start ThreadPoolExecutor for process {} taskGroups", taskGroups.size());
        ThreadPoolExecutor threadPool =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(Detection.DEFAULT_POOL_SIZE);
        List<Future<PlagDetectionResult>> results = new ArrayList<>(taskGroups.size());
        for (TaskGroup taskGroup : taskGroups) {
            PlagDetectionResultCallable callable = new PlagDetectionResultCallable(
                    taskGroupService, plagDetectionService, taskGroup);
            results.add(threadPool.submit(callable));
        }
        threadPool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!threadPool.awaitTermination(Detection.EXECUTION_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES)) {
                threadPool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!threadPool.awaitTermination(Detection.TASK_CANCEL_RESPONSE_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)) {
                    log.error("Thread pool did not terminate");
                }
            }
            for (Future<PlagDetectionResult> result : results) {
                if (!result.isDone()) {
                    result.cancel(true);
                }
                try {
                    log.info("Result of plagiarism detection: {}", result.get().toString());
                } catch (Exception ex) {
                    log.error("Plagiarism detection failed: {}", ex.toString());
                }
            }
        } catch (InterruptedException ex) {
            log.error("AutomatedPlagiarismDetectionJob failed", ex);
            checkTaskGroupStatus(taskGroups);
            // (Re-)Cancel if current thread also interrupted
            threadPool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    private void checkTaskGroupStatus(List<TaskGroup> taskGroups) {
        for (TaskGroup taskGroup : taskGroups) {
            Optional<TaskGroup> taskGroupOpt =
                    taskGroupService.getTaskGroupByIdAndStatus(taskGroup.getId(), PlagDetectionStatus.IN_PROCESS);
            if (taskGroupOpt.isPresent()) {
                TaskGroup completedTaskGroup = taskGroupOpt.get();
                PlagDetectionResult result = PlagDetectionResult.builder()
                        .isSuccessful(false)
                        .date(new Date())
                        .resultMessage("Failed detect plagiarism for task!")
                        .build();
                completedTaskGroup.setPlagDetectionResult(result);
                completedTaskGroup.setPlagDetectionStatus(PlagDetectionStatus.FAILED);
                taskGroupService.saveTaskGroup(taskGroup);
            }
        }
    }

    static class PlagDetectionResultCallable implements Callable<PlagDetectionResult> {
        private final TaskGroupService taskGroupService;
        private final PlagDetectionService plagiarismDetectionService;
        private final TaskGroup taskGroup;

        public PlagDetectionResultCallable(TaskGroupService taskGroupService,
                                           PlagDetectionService plagiarismDetectionService,
                                           TaskGroup taskGroup) {
            this.taskGroupService = taskGroupService;
            this.plagiarismDetectionService = plagiarismDetectionService;
            this.taskGroup = taskGroup;
        }

        @Override
        public PlagDetectionResult call() {
            try {
                taskGroup.setPlagDetectionStatus(PlagDetectionStatus.IN_PROCESS);
                taskGroupService.saveTaskGroup(taskGroup);

                PlagDetectionResult result = plagiarismDetectionService.processForTaskGroup(taskGroup);

                taskGroup.setPlagDetectionResult(result);
                taskGroup.setPlagDetectionStatus(
                        result.getIsSuccessful()
                                ? PlagDetectionStatus.DONE
                                : PlagDetectionStatus.FAILED
                );

                return result;
            } catch (Exception ex) {
                log.error("", ex);
                PlagDetectionResult result = PlagDetectionResult.failed("Failed detect plagiarism for task!");
                taskGroup.setPlagDetectionStatus(PlagDetectionStatus.FAILED);
                taskGroup.setPlagDetectionResult(result);
                throw ex;
            } finally {
                taskGroupService.saveTaskGroup(taskGroup);
            }
        }
    }

}
