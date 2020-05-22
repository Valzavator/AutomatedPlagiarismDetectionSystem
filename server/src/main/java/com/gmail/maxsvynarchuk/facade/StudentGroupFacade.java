package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.request.StudentGroupRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentGroupResponseDto;

public interface StudentGroupFacade {

    StudentGroupResponseDto addStudentToGroup(Long creatorId, StudentGroupRequestDto dto);

    boolean deleteStudentFromGroup(Long studentId, Long groupId);

}
