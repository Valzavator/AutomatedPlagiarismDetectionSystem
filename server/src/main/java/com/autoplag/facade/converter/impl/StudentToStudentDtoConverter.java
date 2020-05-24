package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Student;
import com.autoplag.presentation.payload.response.StudentDto;
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
