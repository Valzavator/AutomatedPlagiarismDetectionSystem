package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> getTaskById(Long taskId);

    List<Task> getAllTasksByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId);

}
