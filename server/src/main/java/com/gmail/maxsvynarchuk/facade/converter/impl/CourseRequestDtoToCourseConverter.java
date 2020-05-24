package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Course;
import com.gmail.maxsvynarchuk.presentation.payload.request.CourseRequestDto;
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
