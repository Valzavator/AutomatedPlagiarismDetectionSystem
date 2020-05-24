package com.autoplag.facade;

import com.autoplag.presentation.payload.request.StudentRequestDto;
import com.autoplag.presentation.payload.response.PagedDto;
import com.autoplag.presentation.payload.response.StudentContainerDto;
import com.autoplag.presentation.payload.response.StudentDto;

public interface StudentFacade {

    PagedDto<StudentDto> getStudentsByCreatorId(Long creatorId, int page, int size);

    StudentContainerDto getStudentsForAddingToCourse(Long userId, Long courseId);

    boolean addStudentToSystem(Long userId, StudentRequestDto dto);

}
