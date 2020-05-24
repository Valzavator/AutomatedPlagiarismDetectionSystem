package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.StudentGroup;
import com.autoplag.presentation.payload.request.StudentGroupRequestDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StudentGroupRequestDtoToStudentGroupConverter implements Converter<StudentGroupRequestDto, StudentGroup>  {
    private final ModelMapper mapper;

    @Override
    public StudentGroup convert(StudentGroupRequestDto studentGroupDto) {
        return mapper.map(studentGroupDto, StudentGroup.class);
    }
}
