package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByCreatorId(Long creatorId, Pageable pageable);

    @Query("SELECT s1 " +
            "FROM Student s1  " +
            "WHERE s1.creator.id = :userId AND s1.id NOT IN (" +
            "SELECT sg.id.studentId FROM StudentGroup sg " +
            "WHERE sg.course.id = :courseId)")
    List<Student> findAllNotAddedToCourse(Long userId, Long courseId, Sort sort);

}