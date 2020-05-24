package com.autoplag.service.impl;

import com.autoplag.persistence.dao.StudentGroupDao;
import com.autoplag.persistence.domain.StudentGroup;
import com.autoplag.persistence.domain.StudentGroupKey;
import com.autoplag.persistence.domain.User;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.service.StudentGroupService;
import com.autoplag.service.UserService;
import com.autoplag.service.vcs.VcsDownloadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentGroupServiceImpl implements StudentGroupService {
    private final VcsDownloadService vcsDownloadService;
    private final UserService userService;
    private final StudentGroupDao studentGroupDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<StudentGroup> getStudentGroupById(StudentGroupKey id) {
        return studentGroupDao.findOne(id);
    }

    @Transactional
    @Override
    public StudentGroup addStudentToGroup(Long creatorId, StudentGroup studentGroup) {
        String repositoryUrl = vcsDownloadService.getRootRepositoryUrl(
                studentGroup.getVcsRepositoryUrl());
        User user = userService.getRequiredUserById(creatorId);
        AccessToken accessToken = user.getAccessToken(repositoryUrl);
        vcsDownloadService.checkAccessToRepository(accessToken, repositoryUrl);
        studentGroup.setVcsRepositoryUrl(repositoryUrl);
        return studentGroupDao.save(studentGroup);
    }

    @Transactional
    @Override
    public boolean deleteStudentFromGroup(StudentGroupKey id) {
        Optional<StudentGroup> studentGroupOpt = studentGroupDao.findOne(id);
        if (studentGroupOpt.isPresent()) {
            studentGroupDao.deleteById(id);
            return true;
        }
        return false;
    }
}
