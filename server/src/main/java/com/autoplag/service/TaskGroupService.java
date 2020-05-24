package com.autoplag.service;

import com.autoplag.persistence.domain.TaskGroup;
import com.autoplag.persistence.domain.TaskGroupKey;
import com.autoplag.persistence.domain.type.PlagDetectionStatus;

import java.util.List;
import java.util.Optional;

public interface TaskGroupService {

    Optional<TaskGroup> getTaskGroupById(TaskGroupKey id);

    void checkTaskGroupNow(TaskGroupKey id);

    Optional<TaskGroup> getTaskGroupByIdAndStatus(TaskGroupKey id, PlagDetectionStatus status);

    List<TaskGroup> getAllExpiredTaskGroupsWithPendingStatus();

    TaskGroup saveTaskGroup(TaskGroup taskGroup);

    boolean deleteTaskGroup(TaskGroupKey id);

}
