package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.TaskFacade;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

}
