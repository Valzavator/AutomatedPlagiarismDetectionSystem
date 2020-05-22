package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.StudentGroup;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentGroupResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StudentGroupToStudentGroupDtoConverter implements Converter<StudentGroup, StudentGroupResponseDto> {
    private final ModelMapper mapper;

    @Override
    public StudentGroupResponseDto convert(StudentGroup studentGroup) {
        return mapper.map(studentGroup, StudentGroupResponseDto.class);
    }
}
