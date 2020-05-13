package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.response.CourseDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;

import java.util.Optional;

public interface CourseFacade {

    PagedDto<CourseDto> getCoursesByCreatorId(Long creatorId, int page, int size);

    Optional<CourseDto> getCourseById(Long creatorId, Long courseId);

}
