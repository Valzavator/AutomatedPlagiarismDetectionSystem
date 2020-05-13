package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.CourseFacade;
import com.gmail.maxsvynarchuk.presentation.payload.response.*;
import com.gmail.maxsvynarchuk.presentation.payload.response.error.ApiError;
import com.gmail.maxsvynarchuk.presentation.security.AuthUser;
import com.gmail.maxsvynarchuk.presentation.security.serivce.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
@Slf4j
public class CoursesController {
    private CourseFacade courseFacade;

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

    @GetMapping("/{courseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCourse(
            @PathVariable(value = "courseId") long courseId,
            @AuthUser UserPrincipal currentUser,
            HttpServletRequest request) {
        Optional<CourseDto> courseDtoOpt = courseFacade.getCourseById(currentUser.getId(), courseId);
        if (courseDtoOpt.isPresent()) {
            return ResponseEntity.ok()
                    .body(courseDtoOpt.get());
        }
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}
