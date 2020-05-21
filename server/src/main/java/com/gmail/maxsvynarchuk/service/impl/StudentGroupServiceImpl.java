package com.gmail.maxsvynarchuk.service.impl;

import com.gmail.maxsvynarchuk.persistence.dao.StudentGroupDao;
import com.gmail.maxsvynarchuk.persistence.domain.StudentGroup;
import com.gmail.maxsvynarchuk.persistence.domain.StudentGroupKey;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.service.StudentGroupService;
import com.gmail.maxsvynarchuk.service.UserService;
import com.gmail.maxsvynarchuk.service.vcs.VcsDownloadService;
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
    public void addStudentToGroup(Long creatorId, StudentGroup studentGroup) {
        String repositoryUrl = studentGroup.getVcsRepositoryUrl();
        User user = userService.getRequiredUserById(creatorId);
        AccessToken accessToken = user.getAccessToken(repositoryUrl);
        vcsDownloadService.checkAccessToRepository(accessToken, repositoryUrl);
        studentGroupDao.save(studentGroup);
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
