package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;

import java.util.List;
import java.util.Optional;

public interface TaskGroupDao extends GenericDao<TaskGroup, TaskGroupKey> {

    List<TaskGroup> findAllExpiredTaskGroupWithPendingStatus();

    Optional<TaskGroup> findByIdAndStatus(TaskGroupKey id, PlagDetectionStatus status);

}
