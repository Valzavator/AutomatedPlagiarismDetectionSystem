package com.autoplag.service.cronjob;

import com.autoplag.persistence.domain.TaskGroup;
import com.autoplag.service.PlagDetectionService;
import com.autoplag.service.TaskGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class AutomatedPlagiarismDetectionScheduler {
    private final PlagDetectionService plagDetectionService;
    private final TaskGroupService taskGroupService;
    private static final int SCHEDULER_TIMEOUT_IN_MS = 30000;

    @Scheduled(fixedRate = SCHEDULER_TIMEOUT_IN_MS)
    public void scheduleTaskWithFixedRate() {
        List<TaskGroup> taskGroups = taskGroupService.getAllExpiredTaskGroupsWithPendingStatus();
        if (!taskGroups.isEmpty()) {
            new AutomatedPlagiarismDetectionJob(taskGroupService, plagDetectionService, taskGroups)
                    .start();
        }
    }

}
