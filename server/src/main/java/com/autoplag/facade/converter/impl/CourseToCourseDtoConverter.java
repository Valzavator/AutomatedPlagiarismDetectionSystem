package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Course;
import com.autoplag.presentation.payload.response.CourseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseToCourseDtoConverter implements Converter<Course, CourseDto> {
    private final ModelMapper mapper;

    @Override
    public CourseDto convert(Course course) {
        return mapper.map(course, CourseDto.class);
    }
}
