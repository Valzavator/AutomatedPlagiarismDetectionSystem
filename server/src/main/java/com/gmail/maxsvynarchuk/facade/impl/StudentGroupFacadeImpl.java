package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.StudentGroupFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.presentation.payload.request.StudentGroupDto;
import com.gmail.maxsvynarchuk.service.CourseService;
import com.gmail.maxsvynarchuk.service.StudentGroupService;
import com.gmail.maxsvynarchuk.service.StudentService;
import lombok.AllArgsConstructor;

import java.util.Optional;

@Facade
@AllArgsConstructor
public class StudentGroupFacadeImpl implements StudentGroupFacade {
    private final CourseService courseService;
    private final StudentGroupService studentGroupService;

    private final Converter<StudentGroupDto, StudentGroup> studentGroupDtoToStudentGroup;

    @Override
    public void addStudentToGroup(Long creatorId, StudentGroupDto dto) {
        Course course = courseService.getCourseById(creatorId, dto.getCourseId())
                .orElseThrow();
        StudentGroup studentGroup = studentGroupDtoToStudentGroup.convert(dto);
        StudentGroupKey studentGroupKey = new StudentGroupKey(dto.getStudentId(), dto.getGroupId());
        studentGroup.setId(studentGroupKey);
        studentGroup.setCourse(course);
        studentGroupService.addStudentToGroup(creatorId, studentGroup);
    }

    @Override
    public boolean deleteStudentFromGroup(Long studentId, Long groupId) {
        StudentGroupKey studentGroupKey = new StudentGroupKey(studentId, groupId);
        return studentGroupService.deleteStudentFromGroup(studentGroupKey);
    }

}
