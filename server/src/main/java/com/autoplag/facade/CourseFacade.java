package com.autoplag.facade;

import com.autoplag.presentation.payload.request.CourseRequestDto;
import com.autoplag.presentation.payload.response.CourseDto;
import com.autoplag.presentation.payload.response.PagedDto;

public interface CourseFacade {

    PagedDto<CourseDto> getCoursesByCreatorId(Long creatorId, int page, int size);

    CourseDto getCourseById(Long creatorId, Long courseId);

    CourseDto addCourseToSystem(Long creatorId, CourseRequestDto dto);

}
