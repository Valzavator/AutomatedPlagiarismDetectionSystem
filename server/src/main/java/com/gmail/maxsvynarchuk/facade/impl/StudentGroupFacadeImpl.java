package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.StudentGroupFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.presentation.exception.BadRequestException;
import com.gmail.maxsvynarchuk.presentation.payload.request.StudentGroupRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentGroupResponseDto;
import com.gmail.maxsvynarchuk.service.CourseService;
import com.gmail.maxsvynarchuk.service.StudentGroupService;
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
