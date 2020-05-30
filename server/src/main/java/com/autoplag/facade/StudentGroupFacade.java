package com.autoplag.facade;

import com.autoplag.presentation.payload.request.StudentGroupRequestDto;
import com.autoplag.presentation.payload.response.StudentGroupResponseDto;

public interface StudentGroupFacade {

    StudentGroupResponseDto addStudentToGroup(Long creatorId, StudentGroupRequestDto dto);

    void deleteStudentFromGroup(Long studentId, Long groupId);

}
