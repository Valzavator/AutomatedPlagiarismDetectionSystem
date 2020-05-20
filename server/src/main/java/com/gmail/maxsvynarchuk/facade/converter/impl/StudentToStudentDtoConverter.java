package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {
    private final ModelMapper mapper;

    @Override
    public StudentDto convert(Student student) {
        return mapper.map(student, StudentDto.class);
    }
}
