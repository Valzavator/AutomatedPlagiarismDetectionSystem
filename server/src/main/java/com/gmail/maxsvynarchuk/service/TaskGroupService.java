package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;

import java.util.List;

public interface TaskGroupService {

    List<TaskGroup> findAllExpiredTaskGroupWithPendingStatus();

}
