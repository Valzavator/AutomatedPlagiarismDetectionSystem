package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.Task;

import java.util.List;

public interface TaskDao extends GenericDao<Task, Long> {

    List<Task> findAllByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId);

}
