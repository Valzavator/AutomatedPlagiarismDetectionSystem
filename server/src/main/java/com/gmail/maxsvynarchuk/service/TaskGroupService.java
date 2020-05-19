package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;

import java.util.List;
import java.util.Optional;

public interface TaskGroupService {

    Optional<TaskGroup> getTaskGroupById(TaskGroupKey id);

    Optional<TaskGroup> getTaskGroupByIdAndStatus(TaskGroupKey id, PlagDetectionStatus status);

    List<TaskGroup> getAllExpiredTaskGroupWithPendingStatus();

    TaskGroup saveTaskGroup(TaskGroup taskGroup);

}
