package com.gmail.maxsvynarchuk.facade.impl;

    import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.StudentFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentDto;
import com.gmail.maxsvynarchuk.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Facade
@AllArgsConstructor
public class StudentFacadeImpl implements StudentFacade {
    private final StudentService studentService;

    private final Converter<Student, StudentDto> studentToStudentDto;

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

}
