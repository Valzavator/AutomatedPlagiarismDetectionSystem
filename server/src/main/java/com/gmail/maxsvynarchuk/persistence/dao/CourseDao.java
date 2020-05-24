package com.gmail.maxsvynarchuk.persistence.dao;

import com.gmail.maxsvynarchuk.persistence.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CourseDao extends GenericDao<Course, Long> {

    Page<Course> findByCreatorId(Long creatorId, Pageable pageable);

    Optional<Course> findByIdAndCreatorId(Long courseId, Long creatorId);

    void deleteByIdAndCreatorId(Long courseId, Long creatorId);

}
