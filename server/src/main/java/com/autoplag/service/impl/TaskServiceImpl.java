package com.autoplag.service.impl;

import com.autoplag.persistence.dao.TaskDao;
import com.autoplag.persistence.domain.Course;
import com.autoplag.persistence.domain.Task;
import com.autoplag.service.CourseService;
import com.autoplag.service.TaskService;
import com.autoplag.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final CourseService courseService;
    private final TaskDao taskDao;

    @Transactional(readOnly = true)
    @Override
    public Task getTaskById(Long taskId) {
        return taskDao.findOne(taskId)
                .orElseThrow(ResourceNotFoundException::new);
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

    @Override
    public Task saveTask(Long creatorId, Long courseId, Task task) {
        Course course = courseService.getCourseById(creatorId, courseId);
        task.setCourse(course);
        return taskDao.save(task);
    }

    @Override
    public void deleteTaskFromCourse(Long taskId) {
        taskDao.deleteById(taskId);
    }

}
