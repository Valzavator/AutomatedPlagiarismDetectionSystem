package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.TaskGroupDao;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import com.gmail.maxsvynarchuk.presentation.exception.BadRequestException;
import com.gmail.maxsvynarchuk.presentation.exception.ResourceNotFoundException;
import com.gmail.maxsvynarchuk.service.PlagDetectionResultService;
import com.gmail.maxsvynarchuk.service.TaskGroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskGroupServiceImpl implements TaskGroupService {
    private final PlagDetectionResultService plagDetectionResultService;
    private final TaskGroupDao taskGroupDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<TaskGroup> getTaskGroupById(TaskGroupKey id) {
        return taskGroupDao.findOne(id);
    }

    @Transactional
    @Override
    public void checkTaskGroupNow(TaskGroupKey id) {
        TaskGroup taskGroup = getTaskGroupById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (taskGroup.getPlagDetectionStatus() == PlagDetectionStatus.IN_PROCESS) {
            throw new BadRequestException();
        }
        taskGroup.setPlagDetectionStatus(PlagDetectionStatus.PENDING);
        taskGroup.setExpiryDate(new Date());
        saveTaskGroup(taskGroup);
    }

    @Override
    public Optional<TaskGroup> getTaskGroupByIdAndStatus(TaskGroupKey id, PlagDetectionStatus status) {
        return taskGroupDao.findByIdAndStatus(id, status);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskGroup> getAllExpiredTaskGroupsWithPendingStatus() {
        return taskGroupDao.findAllExpiredTaskGroupWithPendingStatus();
    }

    @Transactional
    @Override
    public TaskGroup saveTaskGroup(TaskGroup taskGroup) {
        return taskGroupDao.save(taskGroup);
    }

    @Override
    public boolean deleteTaskGroup(TaskGroupKey id) {
        Optional<TaskGroup> taskGroupOpt = taskGroupDao.findOne(id)
                .filter(tg -> tg.getPlagDetectionStatus() != PlagDetectionStatus.IN_PROCESS);
        if (taskGroupOpt.isPresent()) {
            taskGroupDao.deleteById(id);
            return true;
        }
        return false;
    }

}
