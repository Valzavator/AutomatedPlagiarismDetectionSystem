package com.autoplag.presentation.controller;

import com.autoplag.facade.CourseFacade;
import com.autoplag.presentation.payload.response.CourseDto;
import com.autoplag.presentation.payload.response.PagedDto;
import com.autoplag.presentation.security.AuthUser;
import com.autoplag.presentation.security.serivce.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
@Slf4j
public class AllCoursesController {
    private final CourseFacade courseFacade;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PagedDto<CourseDto>> getAllUserCourses(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @AuthUser UserPrincipal currentUser) {
        PagedDto<CourseDto> pagedDto = courseFacade.getCoursesByCreatorId(currentUser.getId(), page, size);
        return ResponseEntity.ok()
                .body(pagedDto);
    }

}
