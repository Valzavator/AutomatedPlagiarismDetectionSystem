package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;

import java.util.List;
import java.util.Optional;

public interface TaskGroupService {

    Optional<TaskGroup> getTaskGroupById(TaskGroupKey id);

    List<TaskGroup> getAllExpiredTaskGroupWithPendingStatus();

    TaskGroup saveTaskGroup(TaskGroup taskGroup);

}
