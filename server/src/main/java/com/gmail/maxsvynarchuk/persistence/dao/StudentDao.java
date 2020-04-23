package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.persistence.domain.Task;

import java.util.Set;

public interface StudentDao extends GenericDao<Student, Long> {

    Set<Student> findAllWhoHaveTask(Task task);

}
