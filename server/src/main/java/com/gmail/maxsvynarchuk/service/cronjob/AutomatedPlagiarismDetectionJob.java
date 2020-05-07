package com.gmail.maxsvynarchuk.service.cronjob;

import com.gmail.maxsvynarchuk.config.constant.Detection;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import com.gmail.maxsvynarchuk.service.PlagiarismDetectionService;
import com.gmail.maxsvynarchuk.service.TaskGroupService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Slf4j
public class AutomatedPlagiarismDetectionJob extends Thread {
    private final List<TaskGroup> taskGroups;
    private final PlagiarismDetectionService plagiarismDetectionService;
    private final TaskGroupService taskGroupService;

    public AutomatedPlagiarismDetectionJob(TaskGroupService taskGroupService,
                                           PlagiarismDetectionService plagiarismDetectionService,
                                           List<TaskGroup> taskGroups) {
        super("AutomatedPlagiarismDetectionJob with taskGroups.size=" + taskGroups.size());
        this.taskGroupService = taskGroupService;
        this.plagiarismDetectionService = plagiarismDetectionService;
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
                    taskGroupService, plagiarismDetectionService, taskGroup);
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
            Optional<TaskGroup> completedTaskGroupOpt =
                    taskGroupService.findById(taskGroup.getId())
                            .filter(tg -> tg.getPlagDetectionStatus() == PlagDetectionStatus.IN_PROCESS);
            if (completedTaskGroupOpt.isPresent()) {
                TaskGroup completedTaskGroup = completedTaskGroupOpt.get();
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
        private final PlagiarismDetectionService plagiarismDetectionService;
        private final TaskGroup taskGroup;

        public PlagDetectionResultCallable(TaskGroupService taskGroupService,
                                           PlagiarismDetectionService plagiarismDetectionService,
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
