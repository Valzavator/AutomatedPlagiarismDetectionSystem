package com.autoplag.persistence.dao;

import com.autoplag.persistence.domain.StudentGroup;
import com.autoplag.persistence.domain.StudentGroupKey;
import com.autoplag.persistence.domain.Task;

import java.util.Set;

public interface StudentGroupDao extends GenericDao<StudentGroup, StudentGroupKey> {

    Set<StudentGroup> findAllWhoHaveTask(Task task);

}
