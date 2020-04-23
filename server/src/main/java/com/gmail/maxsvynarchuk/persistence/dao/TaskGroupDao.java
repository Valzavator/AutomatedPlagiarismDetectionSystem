package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;

import java.util.List;

public interface TaskGroupDao extends GenericDao<TaskGroup, TaskGroupKey> {

    List<TaskGroup> findAllExpiredTaskGroupWithPendingStatus();

}
