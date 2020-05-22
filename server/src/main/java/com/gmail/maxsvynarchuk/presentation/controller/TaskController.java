package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.TaskFacade;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.VCSException;
import com.gmail.maxsvynarchuk.presentation.payload.request.StudentGroupRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentGroupResponseDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskDto;
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
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/tasks")
@AllArgsConstructor
@Slf4j
public class TaskController {
    private final TaskFacade taskFacade;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllTasksForCourse(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "6") int size,
            @PathVariable("courseId") Long courseId) {
        PagedDto<TaskDto> pagedDto = taskFacade.getTasksForCourse(courseId, page, size);
        return ResponseEntity.ok()
                .body(pagedDto);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addTaskToCourse(
            @AuthUser UserPrincipal user,
            @Valid @RequestBody TaskDto dto,
            @PathVariable("courseId") long courseId) {
        System.out.println(dto);
//        try {
//            StudentGroupResponseDto studentGroupDto = studentGroupFacade.addStudentToGroup(user.getId(), dto);
            return ResponseEntity.ok().build();
//        } catch (VCSException ex) {
//            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request.getRequestURI(), ex.getMessage());
//            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//        }
    }

}
