package com.gmail.maxsvynarchuk.persistence.dao.repository;

import com.gmail.maxsvynarchuk.persistence.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByCreatorId(Long creatorId, Pageable pageable);

    Optional<Course> findByIdAndCreatorId(Long courseId, Long creatorId);

    long deleteByIdAndCreatorId(Long courseId, Long creatorId);

}