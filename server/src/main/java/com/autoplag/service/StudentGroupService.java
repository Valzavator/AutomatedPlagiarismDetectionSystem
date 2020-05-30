package com.autoplag.service;

import com.autoplag.persistence.domain.StudentGroup;
import com.autoplag.persistence.domain.StudentGroupKey;

public interface StudentGroupService {

    StudentGroup addStudentToGroup(Long creatorId, Long courseId, StudentGroup studentGroup);

    void deleteStudentFromGroup(StudentGroupKey id);

}
