package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Student;
import com.gmail.maxsvynarchuk.presentation.payload.request.StudentRequestDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StudentRequestDtoToStudentConverter implements Converter<StudentRequestDto, Student>{
    private final ModelMapper mapper;

    @Override
    public Student convert(StudentRequestDto studentRequestDto) {
        return mapper.map(studentRequestDto, Student.class);
    }
}
