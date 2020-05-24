package com.autoplag.presentation.controller;

import com.autoplag.facade.CourseFacade;
import com.autoplag.presentation.payload.request.CourseRequestDto;
import com.autoplag.presentation.payload.response.CourseDto;
import com.autoplag.presentation.security.AuthUser;
import com.autoplag.presentation.security.serivce.UserPrincipal;
import com.autoplag.presentation.util.ControllerUtil;
import com.autoplag.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
@Slf4j
public class CourseController {
    private final CourseService courseService;
    private final CourseFacade courseFacade;

    @GetMapping("/{courseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCourse(
            @PathVariable(value = "courseId") long courseId,
            @AuthUser UserPrincipal currentUser) {
        CourseDto course = courseFacade.getCourseById(currentUser.getId(), courseId);
        return ResponseEntity.ok().body(course);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addCourseToSystem(
            @AuthUser UserPrincipal user,
            @Valid @RequestBody CourseRequestDto dto) {
        CourseDto courseDto = courseFacade.addCourseToSystem(user.getId(), dto);
        URI location = ControllerUtil.getLocation(
                "api/v1/courses", courseDto.getId().toString());
        return ResponseEntity.created(location).body(courseDto);
    }

    @PostMapping("/{courseId}/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCourseFromSystem(
            @AuthUser UserPrincipal user,
            @PathVariable(value = "courseId") long courseId) {
        courseService.deleteCourseFromSystem(user.getId(), courseId);
        return ResponseEntity.noContent().build();
    }

}
