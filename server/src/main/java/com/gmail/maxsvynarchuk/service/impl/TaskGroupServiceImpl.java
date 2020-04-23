package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.TaskGroupDao;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;
import com.gmail.maxsvynarchuk.service.TaskGroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskGroupServiceImpl implements TaskGroupService {
    private final TaskGroupDao taskGroupDao;

    @Override
    public Optional<TaskGroup> findById(TaskGroupKey id) {
        return taskGroupDao.findOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskGroup> findAllExpiredTaskGroupWithPendingStatus() {
        return taskGroupDao.findAllExpiredTaskGroupWithPendingStatus();
    }

    @Transactional
    @Override
    public TaskGroup updateTaskGroup(TaskGroup taskGroup) {
        return taskGroupDao.save(taskGroup);
    }

}