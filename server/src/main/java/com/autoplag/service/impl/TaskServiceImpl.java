package com.autoplag.service.impl;

import com.autoplag.persistence.dao.TaskDao;
import com.autoplag.persistence.domain.Task;
import com.autoplag.service.TaskService;
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

    @Transactional
    @Override
    public Task saveTask(Task task) {
        return taskDao.save(task);
    }

    @Override
    public boolean deleteTaskFromCourse(Long taskId) {
        Optional<Task> taskOpt = taskDao.findOne(taskId);
        if (taskOpt.isPresent()) {
            taskDao.deleteById(taskId);
            return true;
        }
        return false;
    }
}
