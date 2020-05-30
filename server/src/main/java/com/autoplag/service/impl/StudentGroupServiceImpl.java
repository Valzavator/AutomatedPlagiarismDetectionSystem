package com.autoplag.service.impl;

import com.autoplag.persistence.dao.StudentGroupDao;
import com.autoplag.persistence.domain.Course;
import com.autoplag.persistence.domain.StudentGroup;
import com.autoplag.persistence.domain.StudentGroupKey;
import com.autoplag.persistence.domain.User;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.service.CourseService;
import com.autoplag.service.StudentGroupService;
import com.autoplag.service.UserService;
import com.autoplag.service.vcs.VcsDownloadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class StudentGroupServiceImpl implements StudentGroupService {
    private final VcsDownloadService vcsDownloadService;
    private final UserService userService;
    private final CourseService courseService;
    private final StudentGroupDao studentGroupDao;

    @Override
    public StudentGroup addStudentToGroup(Long creatorId, Long courseId, StudentGroup studentGroup) {
        String repositoryUrl = vcsDownloadService.getRootRepositoryUrl(
                studentGroup.getVcsRepositoryUrl());

        User user = userService.getUserById(creatorId);
        AccessToken accessToken = user.getAccessToken(repositoryUrl);
        vcsDownloadService.checkAccessToRepository(accessToken, repositoryUrl);
        studentGroup.setVcsRepositoryUrl(repositoryUrl);

        Course course = courseService.getCourseById(creatorId, courseId);
        studentGroup.setCourse(course);

        return studentGroupDao.save(studentGroup);
    }

    @Override
    public void deleteStudentFromGroup(StudentGroupKey id) {
        studentGroupDao.deleteById(id);
    }
}
