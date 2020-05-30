package com.autoplag.facade.impl;

import com.autoplag.facade.CourseFacade;
import com.autoplag.facade.Facade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Course;
import com.autoplag.presentation.payload.request.CourseRequestDto;
import com.autoplag.presentation.payload.response.CourseDto;
import com.autoplag.presentation.payload.response.PagedDto;
import com.autoplag.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Facade
@AllArgsConstructor
public class CourseFacadeImpl implements CourseFacade {
    private final CourseService courseService;

    private final Converter<CourseRequestDto, Course> courseRequestDtoToCourse;
    private final Converter<Course, CourseDto> courseToCourseDto;

    @Override
    public PagedDto<CourseDto> getCoursesByCreatorId(Long creatorId, int page, int size) {
        Page<Course> coursesPage = courseService.getAllCoursesByCreatorId(creatorId, page, size);
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
    public CourseDto getCourseById(Long creatorId, Long courseId) {
        Course course = courseService.getCourseById(creatorId, courseId);
        return courseToCourseDto.convert(course);
    }

    @Override
    public CourseDto addCourseToSystem(Long creatorId, CourseRequestDto dto) {
        Course course = courseRequestDtoToCourse.convert(dto);
        course = courseService.saveCourse(creatorId, course);
        return courseToCourseDto.convert(course);
    }

}
