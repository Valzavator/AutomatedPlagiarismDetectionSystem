package com.autoplag.service;

import com.autoplag.persistence.domain.StudentGroup;
import com.autoplag.persistence.domain.StudentGroupKey;

import java.util.Optional;

public interface StudentGroupService {

    Optional<StudentGroup> getStudentGroupById(StudentGroupKey id);

    StudentGroup addStudentToGroup(Long creatorId, StudentGroup studentGroup);

    boolean deleteStudentFromGroup(StudentGroupKey id);

}
