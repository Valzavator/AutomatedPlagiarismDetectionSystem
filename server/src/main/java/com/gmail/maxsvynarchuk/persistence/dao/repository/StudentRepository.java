package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT DISTINCT s " +
            "FROM Task t " +
            "JOIN t.taskGroups tg " +
            "JOIN tg.group g " +
            "JOIN g.students s " +
            "WHERE t = :task")
    List<Student> findAllWhoHaveTask(@Param("task") Task task);

}