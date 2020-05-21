package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.request.StudentGroupDto;

public interface StudentGroupFacade {

    void addStudentToGroup(Long creatorId, StudentGroupDto dto);

    boolean deleteStudentFromGroup(Long studentId, Long groupId);

}
