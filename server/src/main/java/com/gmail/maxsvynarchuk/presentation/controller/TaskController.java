package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.TaskFacade;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.VCSException;
import com.gmail.maxsvynarchuk.presentation.payload.request.StudentGroupRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.request.TaskRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentGroupResponseDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.error.ApiError;
import com.gmail.maxsvynarchuk.presentation.security.AuthUser;
import com.gmail.maxsvynarchuk.presentation.security.serivce.UserPrincipal;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/tasks")
@AllArgsConstructor
@Slf4j
public class TaskController {
    private final TaskFacade taskFacade;
    private final TaskService taskService;

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
            @Valid @RequestBody TaskRequestDto dto) {
        taskFacade.addTaskToCourse(user.getId(), dto);
        URI location = ControllerUtil.getLocation("api/v1/courses",
                dto.getCourseId().toString(), "tasks");
        return ResponseEntity.created(location).body(dto);
    }

    @PostMapping("/{taskId}/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteTaskFromCourse(
            @PathVariable(value = "taskId") long taskId,
            HttpServletRequest request) {
        try {
            return taskService.deleteTaskFromCourse(taskId)
                    ? ResponseEntity.noContent().build()
                    : ControllerUtil.notFoundError(request.getRequestURI());
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiError(HttpStatus.CONFLICT,
                            request.getRequestURI(),
                            "The task is assigned to groups!"));
        }
    }

}
