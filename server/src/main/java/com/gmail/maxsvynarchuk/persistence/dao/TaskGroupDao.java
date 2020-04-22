package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;

import java.util.List;

public interface TaskGroupDao extends GenericDao<TaskGroup, Long> {

    List<TaskGroup> findAllExpiredTaskGroupWithPendingStatus();

}
