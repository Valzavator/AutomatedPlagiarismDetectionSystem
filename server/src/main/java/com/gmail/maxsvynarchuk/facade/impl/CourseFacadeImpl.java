package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.CourseFacade;
import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Course;
import com.gmail.maxsvynarchuk.presentation.payload.response.CourseDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Facade
@AllArgsConstructor
public class CourseFacadeImpl implements CourseFacade {
    private final CourseService courseService;

    private final Converter<Course, CourseDto> courseToCourseDto;

    @Override
    public PagedDto<CourseDto> getCoursesByCreatorId(Long creatorId, int page, int size) {
        Page<Course> coursesPage = courseService.getCoursesByCreatorId(creatorId, page, size);
        List<CourseDto> courseDtos = courseToCourseDto.convertAll(coursesPage.getContent());
        return PagedDto.<CourseDto>builder()
                .content(courseDtos)
                .page(coursesPage.getNumber())
                .size(coursesPage.getSize())
                .totalElements(coursesPage.getTotalElements())
                .totalPages(coursesPage.getTotalPages())
                .build();
    }

    @Override
    public Optional<CourseDto> getCourseById(Long creatorId, Long courseId) {
        Optional<Course> courseOpt = courseService.getCourseById(creatorId, courseId);
        return courseOpt.map(courseToCourseDto::convert);
    }

}
