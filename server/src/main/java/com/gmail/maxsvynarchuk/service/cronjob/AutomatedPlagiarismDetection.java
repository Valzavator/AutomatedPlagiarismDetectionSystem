package com.gmail.maxsvynarchuk.service.cronjob;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.service.TaskGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class AutomatedPlagiarismDetection {
    private final TaskGroupService taskGroupService;

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void scheduleTaskWithFixedRate() {
        log.error("\nAutomatedPlagiarismDetection\n");
        List<TaskGroup> taskGroups = taskGroupService.findAllExpiredTaskGroupWithPendingStatus();
        log.error(taskGroups.toString());
    }

}
