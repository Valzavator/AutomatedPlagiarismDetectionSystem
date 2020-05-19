package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    //    @Query("SELECT t " +
//            "FROM Group g " +
//            "JOIN g.course c " +
//            "JOIN g.taskGroups tg " +
//            "JOIN c.tasks t " +
//            "WHERE g.id = :groupId AND c.id = :courseId AND t.id <> tg.id.taskId")
    @Query("SELECT t1 " +
            "FROM Task t1  " +
            "WHERE t1.course.id = :courseId AND t1 NOT IN (" +
            "SELECT t2 FROM Group g " +
            "JOIN g.taskGroups tg " +
            "JOIN tg.task t2 " +
            "WHERE g.id = :groupId)")
//            "JOIN g.course c "+
//            "JOIN g.taskGroups tg " +
//            "JOIN c.tasks t " +
//            "WHERE g.id = :groupId AND c.id = :courseId AND t.id <> tg.id.taskId")
    List<Task> findAllByCourseIdAndNotAssignedToGroup(Long courseId, Long groupId);

}