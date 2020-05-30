package com.autoplag.service;

import com.autoplag.persistence.domain.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

    Task getTaskById(Long taskId);

    Page<Task> getAllTasksByCourseId(Long courseId, int page, int size);

    List<Task> getAllTasksByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId);

    Task saveTask(Long creatorId, Long courseId, Task task);

    void deleteTaskFromCourse(Long taskId);

}
