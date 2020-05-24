package com.autoplag.facade.impl;

import com.autoplag.facade.Facade;
import com.autoplag.facade.StudentGroupFacade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Course;
import com.autoplag.persistence.domain.StudentGroup;
import com.autoplag.persistence.domain.StudentGroupKey;
import com.autoplag.presentation.exception.BadRequestException;
import com.autoplag.presentation.payload.request.StudentGroupRequestDto;
import com.autoplag.presentation.payload.response.StudentGroupResponseDto;
import com.autoplag.service.CourseService;
import com.autoplag.service.StudentGroupService;
import lombok.AllArgsConstructor;


@Facade
@AllArgsConstructor
public class StudentGroupFacadeImpl implements StudentGroupFacade {
    private final CourseService courseService;
    private final StudentGroupService studentGroupService;

    private final Converter<StudentGroupRequestDto, StudentGroup> studentGroupRequestDtoToStudentGroup;
    private final Converter<StudentGroup, StudentGroupResponseDto> studentGroupToStudentGroupResponseDto;

    @Override
    public StudentGroupResponseDto addStudentToGroup(Long creatorId, StudentGroupRequestDto dto) {
        Course course = courseService.getCourseById(creatorId, dto.getCourseId())
                .orElseThrow(BadRequestException::new);
        StudentGroup studentGroup = studentGroupRequestDtoToStudentGroup.convert(dto);
        StudentGroupKey studentGroupKey = new StudentGroupKey(dto.getStudentId(), dto.getGroupId());
        studentGroup.setId(studentGroupKey);
        studentGroup.setCourse(course);
        StudentGroup newStudentGroup = studentGroupService.addStudentToGroup(creatorId, studentGroup);
        return studentGroupToStudentGroupResponseDto.convert(newStudentGroup);
    }

    @Override
    public boolean deleteStudentFromGroup(Long studentId, Long groupId) {
        StudentGroupKey studentGroupKey = new StudentGroupKey(studentId, groupId);
        return studentGroupService.deleteStudentFromGroup(studentGroupKey);
    }

}
