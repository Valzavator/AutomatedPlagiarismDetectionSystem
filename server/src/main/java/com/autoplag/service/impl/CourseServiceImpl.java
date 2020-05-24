package com.autoplag.service.impl;

import com.autoplag.persistence.dao.CourseDao;
import com.autoplag.persistence.domain.Course;
import com.autoplag.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseDao courseDao;

    @Transactional(readOnly = true)
    @Override
    public Page<Course> getAllCoursesByCreatorId(Long creatorId,
                                                 int page,
                                                 int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("creationDate")));
        return courseDao.findByCreatorId(creatorId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Course> getCourseById(Long creatorId, Long courseId) {
        return courseDao.findByIdAndCreatorId(courseId, creatorId);
    }

    @Transactional
    @Override
    public Course saveCourse(Course course) {
        course.setCreationDate(new Date());
        return courseDao.save(course);
    }

    @Transactional
    @Override
    public void deleteCourseFromSystem(Long creatorId, Long courseId) {
        courseDao.deleteByIdAndCreatorId(courseId, creatorId);
    }

}
