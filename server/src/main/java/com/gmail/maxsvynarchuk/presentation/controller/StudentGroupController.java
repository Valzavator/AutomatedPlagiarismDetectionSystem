package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.StudentFacade;
import com.gmail.maxsvynarchuk.facade.TaskGroupFacade;
import com.gmail.maxsvynarchuk.presentation.payload.request.TaskGroupPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.*;
import com.gmail.maxsvynarchuk.presentation.security.AuthUser;
import com.gmail.maxsvynarchuk.presentation.security.serivce.UserPrincipal;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/courses/{courseId}/groups/{groupId}/students")
@AllArgsConstructor
@Slf4j
public class StudentGroupController {
    private final StudentFacade studentFacade;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllStudentsForAddingToCourse(
            @PathVariable(value = "courseId") long courseId,
            @AuthUser UserPrincipal user) {
        StudentContainerDto students = studentFacade.getStudentsForAddingToCourse(user.getId(), courseId);
        return ResponseEntity.ok()
                .body(students);
    }

//    @PostMapping("/{taskId}/delete")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> deleteTaskGroup(
//            @PathVariable(value = "taskId") long taskId,
//            @PathVariable(value = "groupId") long groupId) {
//        return taskGroupFacade.deleteTaskGroup(taskId, groupId)
//                ? ResponseEntity.noContent().build()
//                : ResponseEntity.badRequest().build();
//    }

}
