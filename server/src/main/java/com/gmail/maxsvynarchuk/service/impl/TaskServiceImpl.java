package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.TaskDao;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import com.gmail.maxsvynarchuk.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskDao taskDao;

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return taskDao.findOne(taskId);
    }

    @Transactional
    @Override
    public List<Task> getAllTasksByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId) {
        return taskDao.findAllByCourseIdAndNotAssignedToGroup(courseId, groupId);
    }
}
