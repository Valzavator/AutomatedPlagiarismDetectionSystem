package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasksByCourseId(Long courseId);

}
