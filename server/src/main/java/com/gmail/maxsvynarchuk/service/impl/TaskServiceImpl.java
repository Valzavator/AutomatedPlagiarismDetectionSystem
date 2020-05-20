package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.TaskDao;
import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import com.gmail.maxsvynarchuk.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskDao taskDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return taskDao.findOne(taskId);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Task> getAllTasksByCourseId(Long courseId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("id")));
        return taskDao.findAllByCourseId(courseId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> getAllTasksByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId) {
        return taskDao.findAllByCourseIdAndNotAssignedToGroup(courseId, groupId);
    }
}
