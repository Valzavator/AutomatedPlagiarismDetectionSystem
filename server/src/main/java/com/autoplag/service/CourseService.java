package com.autoplag.service;

import com.autoplag.persistence.domain.Course;
import org.springframework.data.domain.Page;

public interface CourseService {

    Page<Course> getAllCoursesByCreatorId(Long creatorId, int page, int size);

    Course getCourseById(Long creatorId, Long courseId);

    Course saveCourse(Long creatorId, Course course);

    void deleteCourseFromSystem(Long creatorId, Long courseId);

}
