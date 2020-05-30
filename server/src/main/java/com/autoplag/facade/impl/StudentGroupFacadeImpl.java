package com.autoplag.facade.impl;

import com.autoplag.facade.Facade;
import com.autoplag.facade.StudentGroupFacade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.StudentGroup;
import com.autoplag.persistence.domain.StudentGroupKey;
import com.autoplag.presentation.payload.request.StudentGroupRequestDto;
import com.autoplag.presentation.payload.response.StudentGroupResponseDto;
import com.autoplag.service.StudentGroupService;
import lombok.AllArgsConstructor;


@Facade
@AllArgsConstructor
public class StudentGroupFacadeImpl implements StudentGroupFacade {
    private final StudentGroupService studentGroupService;

    private final Converter<StudentGroupRequestDto, StudentGroup> studentGroupRequestDtoToStudentGroup;
    private final Converter<StudentGroup, StudentGroupResponseDto> studentGroupToStudentGroupResponseDto;

    @Override
    public StudentGroupResponseDto addStudentToGroup(Long creatorId, StudentGroupRequestDto dto) {
        StudentGroupKey studentGroupKey = new StudentGroupKey(dto.getStudentId(), dto.getGroupId());
        StudentGroup studentGroup = studentGroupRequestDtoToStudentGroup.convert(dto);
        studentGroup.setId(studentGroupKey);
        StudentGroup newStudentGroup = studentGroupService.addStudentToGroup(creatorId, dto.getCourseId(), studentGroup);
        return studentGroupToStudentGroupResponseDto.convert(newStudentGroup);
    }

    @Override
    public void deleteStudentFromGroup(Long studentId, Long groupId) {
        StudentGroupKey studentGroupKey = new StudentGroupKey(studentId, groupId);
        studentGroupService.deleteStudentFromGroup(studentGroupKey);
    }

}
