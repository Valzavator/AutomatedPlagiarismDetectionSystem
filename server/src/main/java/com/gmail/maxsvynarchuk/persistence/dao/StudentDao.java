package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.persistence.domain.Task;

import java.util.List;

public interface StudentDao extends GenericDao<Student, Long> {

    List<Student> findAllWhoHaveTask(Task task);

}
