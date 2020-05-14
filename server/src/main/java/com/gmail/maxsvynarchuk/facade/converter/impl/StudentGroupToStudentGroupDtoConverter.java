package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.StudentGroup;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.presentation.payload.request.SignUpDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentGroupDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StudentGroupToStudentGroupDtoConverter implements Converter<StudentGroup, StudentGroupDto> {
    private final ModelMapper mapper;

    @Override
    public StudentGroupDto convert(StudentGroup studentGroup) {
        return mapper.map(studentGroup, StudentGroupDto.class);
    }
}
