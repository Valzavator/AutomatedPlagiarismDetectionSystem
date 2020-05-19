package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.CourseFacade;
import com.gmail.maxsvynarchuk.presentation.payload.response.*;
import com.gmail.maxsvynarchuk.presentation.security.AuthUser;
import com.gmail.maxsvynarchuk.presentation.security.serivce.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
