package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.Course;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CourseService {

    Page<Course> getCoursesByCreatorId(Long creatorId, int page, int size);

    Optional<Course> getCourseById(Long creatorId, Long courseId);

}