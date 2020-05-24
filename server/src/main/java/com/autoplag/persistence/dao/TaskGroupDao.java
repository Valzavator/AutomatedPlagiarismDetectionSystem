package com.autoplag.persistence.dao;

import com.autoplag.persistence.domain.TaskGroup;
import com.autoplag.persistence.domain.TaskGroupKey;
import com.autoplag.persistence.domain.type.PlagDetectionStatus;

import java.util.List;
import java.util.Optional;

public interface TaskGroupDao extends GenericDao<TaskGroup, TaskGroupKey> {

    List<TaskGroup> findAllExpiredTaskGroupWithPendingStatus();

    Optional<TaskGroup> findByIdAndStatus(TaskGroupKey id, PlagDetectionStatus status);

}
