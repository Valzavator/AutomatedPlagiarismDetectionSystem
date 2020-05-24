package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.CourseFacade;
import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Course;
import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.presentation.exception.ResourceNotFoundException;
import com.gmail.maxsvynarchuk.presentation.payload.request.CourseRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.CourseDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.service.CourseService;
import com.gmail.maxsvynarchuk.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Facade
@AllArgsConstructor
public class CourseFacadeImpl implements CourseFacade {
    private final UserService userService;
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
        Course course = courseService.getCourseById(creatorId, courseId)
                .orElseThrow(ResourceNotFoundException::new);
        return courseToCourseDto.convert(course);
    }

    @Override
    public CourseDto addCourseToSystem(Long creatorId, CourseRequestDto dto) {
        User creator = userService.getRequiredUserById(creatorId);
        Course course = courseRequestDtoToCourse.convert(dto);
        course.setCreator(creator);
        course = courseService.saveCourse(course);
        return courseToCourseDto.convert(course);
    }

}
