package com.autoplag.service.impl;

import com.autoplag.persistence.dao.CourseDao;
import com.autoplag.persistence.domain.Course;
import com.autoplag.persistence.domain.User;
import com.autoplag.service.CourseService;
import com.autoplag.service.UserService;
import com.autoplag.service.exception.ResourceNotFoundException;
import com.autoplag.util.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final UserService userService;
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
    public Course getCourseById(Long creatorId, Long courseId) {
        return courseDao.findByIdAndCreatorId(courseId, creatorId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Course saveCourse(Long creatorId, Course course) {
        User creator = userService.getUserById(creatorId);
        course.setCreator(creator);
        course.setCreationDate(new Date());
        course.setName(StringUtil.processWhitespace(
                course.getName()
        ));
        return courseDao.save(course);
    }

    @Override
    public void deleteCourseFromSystem(Long creatorId, Long courseId) {
        courseDao.deleteByIdAndCreatorId(courseId, creatorId);
    }

}
