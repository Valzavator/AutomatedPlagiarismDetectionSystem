package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.StudentGroup;
import com.autoplag.persistence.domain.StudentGroupKey;
import com.autoplag.persistence.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, StudentGroupKey> {

    @Query("SELECT DISTINCT sg " +
            "FROM Task t " +
            "JOIN t.taskGroups tg " +
            "JOIN tg.group g " +
            "JOIN g.studentGroups sg " +
            "JOIN sg.student s " +
            "WHERE t = :task")
    Set<StudentGroup> findAllWhoHaveTask(@Param("task") Task task);

}