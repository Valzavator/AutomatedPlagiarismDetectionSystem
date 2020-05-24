package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.request.CourseRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.CourseDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;

public interface CourseFacade {

    PagedDto<CourseDto> getCoursesByCreatorId(Long creatorId, int page, int size);

    CourseDto getCourseById(Long creatorId, Long courseId);

    CourseDto addCourseToSystem(Long creatorId, CourseRequestDto dto);

}
