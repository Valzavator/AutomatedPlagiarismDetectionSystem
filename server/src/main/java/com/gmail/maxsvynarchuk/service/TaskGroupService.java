package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;

import java.util.List;
import java.util.Optional;

public interface TaskGroupService {

    Optional<TaskGroup> findById(TaskGroupKey id);

    List<TaskGroup> findAllExpiredTaskGroupWithPendingStatus();

    TaskGroup updateTaskGroup(TaskGroup taskGroup);

}
