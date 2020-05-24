package com.autoplag.persistence.dao.repository;

import com.autoplag.persistence.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByCourseId(Long courseId, Pageable pageable);

    @Query("SELECT t1 " +
            "FROM Task t1  " +
            "WHERE t1.course.id = :courseId AND t1 NOT IN (" +
            "SELECT t2 FROM Group g " +
            "JOIN g.taskGroups tg " +
            "JOIN tg.task t2 " +
            "WHERE g.id = :groupId)")
    List<Task> findAllByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId);

}