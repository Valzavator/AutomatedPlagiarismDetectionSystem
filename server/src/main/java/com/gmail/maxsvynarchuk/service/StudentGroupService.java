package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.domain.StudentGroup;
import com.gmail.maxsvynarchuk.persistence.domain.StudentGroupKey;

import java.util.Optional;

public interface StudentGroupService {

    Optional<StudentGroup> getStudentGroupById(StudentGroupKey id);

    void addStudentToGroup(Long creatorId, StudentGroup studentGroup);

    boolean deleteStudentFromGroup(StudentGroupKey id);

}
