package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.StudentGroup;
import com.gmail.maxsvynarchuk.persistence.domain.Task;

import java.util.Set;

public interface StudentGroupDao extends GenericDao<StudentGroup, Long> {

    Set<StudentGroup> findAllWhoHaveTask(Task task);

}
