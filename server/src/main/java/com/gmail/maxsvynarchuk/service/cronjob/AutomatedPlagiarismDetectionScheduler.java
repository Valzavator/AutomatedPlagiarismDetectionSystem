package com.gmail.maxsvynarchuk.service.cronjob;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.service.PlagiarismDetectionService;
import com.gmail.maxsvynarchuk.service.TaskGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class AutomatedPlagiarismDetectionScheduler {
    private final PlagiarismDetectionService plagiarismDetectionService;
    private final TaskGroupService taskGroupService;
    private static final int SCHEDULER_TIMEOUT_IN_MS = 30000;

    @Scheduled(fixedRate = SCHEDULER_TIMEOUT_IN_MS)
    public void scheduleTaskWithFixedRate() {
        List<TaskGroup> taskGroups = taskGroupService.findAllExpiredTaskGroupWithPendingStatus();
        if (!taskGroups.isEmpty()) {
            new AutomatedPlagiarismDetectionJob(taskGroupService, plagiarismDetectionService, taskGroups)
                    .start();
        }
    }

}
