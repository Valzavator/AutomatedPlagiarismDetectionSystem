package com.autoplag.presentation.controller;

import com.autoplag.facade.StudentFacade;
import com.autoplag.presentation.payload.request.StudentRequestDto;
import com.autoplag.presentation.payload.response.PagedDto;
import com.autoplag.presentation.payload.response.StudentDto;
import com.autoplag.presentation.payload.response.error.ApiError;
import com.autoplag.presentation.security.AuthUser;
import com.autoplag.presentation.security.serivce.UserPrincipal;
import com.autoplag.presentation.util.ControllerUtil;
import com.autoplag.service.StudentService;
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
@RequestMapping("/api/v1/students")
@AllArgsConstructor
@Slf4j
public class StudentsController {
    private final StudentFacade studentFacade;
    private final StudentService studentService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllUserStudents(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @AuthUser UserPrincipal currentUser) {
        PagedDto<StudentDto> pagedDto = studentFacade.getStudentsByCreatorId(currentUser.getId(), page, size);
        return ResponseEntity.ok()
                .body(pagedDto);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addStudentToSystem(
            @AuthUser UserPrincipal user,
            @Valid @RequestBody StudentRequestDto dto,
            HttpServletRequest request) {
        boolean isSuccessful = studentFacade.addStudentToSystem(user.getId(), dto);
        if (isSuccessful) {
            URI location = ControllerUtil.getLocation("/api/v1/students");
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.badRequest().body(
                    new ApiError(HttpStatus.BAD_REQUEST,
                            request.getRequestURI(),
                            "The full name for the student is already taken!"));
        }
    }

    @PostMapping("/{studentId}/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteStudentFromSystem(
            @PathVariable(value = "studentId") long studentId,
            HttpServletRequest request) {
        try {
            return studentService.deleteStudentFromSystem(studentId)
                    ? ResponseEntity.noContent().build()
                    : ControllerUtil.notFoundError(request.getRequestURI());
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiError(HttpStatus.CONFLICT,
                    request.getRequestURI(),
                    "The student is attached to groups!"));
        }
    }

}
