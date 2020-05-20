package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.TaskGroupDao;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import com.gmail.maxsvynarchuk.service.PlagDetectionResultService;
import com.gmail.maxsvynarchuk.service.TaskGroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void deleteTaskGroup(TaskGroupKey id) {
        taskGroupDao.deleteById(id);
    }

}
