package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.StudentFacade;
import com.gmail.maxsvynarchuk.facade.StudentGroupFacade;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import com.gmail.maxsvynarchuk.presentation.payload.request.StudentGroupDto;
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
import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/courses/{courseId}/groups/{groupId}/students")
@AllArgsConstructor
@Slf4j
public class StudentGroupController {
    private final StudentFacade studentFacade;
    private final StudentGroupFacade studentGroupFacade;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllStudentsForAddingToCourse(
            @PathVariable(value = "courseId") long courseId,
            @AuthUser UserPrincipal user) {
        StudentContainerDto students = studentFacade.getStudentsForAddingToCourse(user.getId(), courseId);
        return ResponseEntity.ok()
                .body(students);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addStudentToGroup(
            @AuthUser UserPrincipal user,
            @Valid @RequestBody StudentGroupDto dto,
            HttpServletRequest request) {
        try {
            studentGroupFacade.addStudentToGroup(user.getId(), dto);
            return ResponseEntity.ok().build();
        } catch (InvalidVcsUrlException ex) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, request.getRequestURI(), ex);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{studentId}/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteStudentFromGroup(
            @PathVariable(value = "studentId") long studentId,
            @PathVariable(value = "groupId") long groupId) {
        return studentGroupFacade.deleteStudentFromGroup(studentId, groupId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().build();
    }

}
