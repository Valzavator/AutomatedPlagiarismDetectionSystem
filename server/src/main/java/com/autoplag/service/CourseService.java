package com.autoplag.service;

import com.autoplag.persistence.domain.Course;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CourseService {

    Page<Course> getAllCoursesByCreatorId(Long creatorId, int page, int size);

    Optional<Course> getCourseById(Long creatorId, Long courseId);

    Course saveCourse(Course course);

    void deleteCourseFromSystem(Long creatorId, Long courseId);

}
