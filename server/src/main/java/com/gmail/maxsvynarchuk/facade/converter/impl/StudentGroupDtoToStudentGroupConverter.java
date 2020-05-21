package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.StudentGroup;
import com.gmail.maxsvynarchuk.presentation.payload.request.StudentGroupDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StudentGroupDtoToStudentGroupConverter implements Converter<StudentGroupDto, StudentGroup>  {
    private final ModelMapper mapper;

    @Override
    public StudentGroup convert(StudentGroupDto studentGroupDto) {
        return mapper.map(studentGroupDto, StudentGroup.class);
    }
}
