package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Course;
import com.autoplag.presentation.payload.request.CourseRequestDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseRequestDtoToCourseConverter implements Converter<CourseRequestDto, Course> {
    private final ModelMapper mapper;

    @Override
    public Course convert(CourseRequestDto courseRequestDto) {
        return mapper.map(courseRequestDto, Course.class);
    }
}
