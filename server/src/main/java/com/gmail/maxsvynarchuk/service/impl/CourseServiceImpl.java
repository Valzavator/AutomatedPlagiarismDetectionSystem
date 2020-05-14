package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.CourseDao;
import com.gmail.maxsvynarchuk.persistence.domain.Course;
import com.gmail.maxsvynarchuk.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseDao courseDao;

    @Transactional(readOnly = true)
    @Override
    public Page<Course> getCoursesByCreatorId(Long userId,
                                              int page,
                                              int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("creationDate")));
        return courseDao.findByCreatorId(userId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Course> getCourseById(Long creatorId, Long courseId) {
        return courseDao.findByIdAndCreatorId(courseId, creatorId);
    }

}
