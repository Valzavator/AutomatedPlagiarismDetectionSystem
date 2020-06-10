package com.autoplag.service.impl;

import com.autoplag.persistence.dao.GroupDao;
import com.autoplag.persistence.domain.Course;
import com.autoplag.persistence.domain.Group;
import com.autoplag.service.CourseService;
import com.autoplag.service.GroupService;
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
public class GroupServiceImpl implements GroupService {
    private final CourseService courseService;
    private final GroupDao groupDao;

    @Transactional(readOnly = true)
    @Override
    public Page<Group> getAllGroupsByCourseId(Long courseId,
                                              int page,
                                              int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("creationDate")));
        return groupDao.findByCourseId(courseId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Group getGroupById(Long groupId) {
        return groupDao.findOne(groupId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Group saveGroup(Long creatorId, Long courseId, Group group) {
        Course course = courseService.getCourseById(creatorId, courseId);
        group.setCourse(course);
        group.setCreationDate(new Date());
        group.setName(StringUtil.processWhitespace(
                group.getName()
        ));
        return groupDao.save(group);
    }

    @Override
    public void deleteGroupFromCourse(Long groupId, Long courseId) {
        groupDao.deleteByIdAndCourseId(groupId, courseId);
    }

}
