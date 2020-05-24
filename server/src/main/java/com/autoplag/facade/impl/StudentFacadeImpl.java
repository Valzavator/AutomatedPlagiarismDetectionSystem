package com.autoplag.facade.impl;

import com.autoplag.facade.Facade;
import com.autoplag.facade.StudentFacade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Student;
import com.autoplag.persistence.domain.User;
import com.autoplag.presentation.payload.request.StudentRequestDto;
import com.autoplag.presentation.payload.response.PagedDto;
import com.autoplag.presentation.payload.response.StudentContainerDto;
import com.autoplag.presentation.payload.response.StudentDto;
import com.autoplag.service.StudentService;
import com.autoplag.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Facade
@AllArgsConstructor
public class StudentFacadeImpl implements StudentFacade {
    private final UserService userService;
    private final StudentService studentService;

    private final Converter<Student, StudentDto> studentToStudentDto;
    private final Converter<StudentRequestDto, Student> studentRequestDtoToStudent;

    @Override
    public PagedDto<StudentDto> getStudentsByCreatorId(Long creatorId, int page, int size) {
        Page<Student> studentsPage = studentService.getAllStudentsByCreatorId(creatorId, page, size);
        List<StudentDto> studentDtos = studentToStudentDto.convertAll(studentsPage.getContent());
        return PagedDto.<StudentDto>builder()
                .content(studentDtos)
                .page(studentsPage.getNumber())
                .size(studentsPage.getSize())
                .totalElements(studentsPage.getTotalElements())
                .totalPages(studentsPage.getTotalPages())
                .build();
    }

    @Override
    public StudentContainerDto getStudentsForAddingToCourse(Long userId, Long courseId) {
        List<Student> students = studentService.getAllStudentsNotAddedToCourse(userId, courseId);
        List<StudentDto> studentDtos = studentToStudentDto.convertAll(students);
        return new StudentContainerDto(studentDtos);
    }

    @Override
    public boolean addStudentToSystem(Long userId, StudentRequestDto dto) {
        User user = userService.getRequiredUserById(userId);
        Student student = studentRequestDtoToStudent.convert(dto);
        student.setCreator(user);
        return studentService.saveStudent(student);
    }

}
