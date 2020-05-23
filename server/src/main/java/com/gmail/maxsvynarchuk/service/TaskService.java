package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.StudentGroupKey;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> getTaskById(Long taskId);

    Page<Task> getAllTasksByCourseId(Long courseId, int page, int size);

    List<Task> getAllTasksByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId);

    Task saveTask(Task task);

    boolean deleteTaskFromCourse(Long taskId);

}
