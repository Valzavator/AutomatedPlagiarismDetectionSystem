package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.StudentFacade;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.VCSException;
import com.gmail.maxsvynarchuk.presentation.payload.request.StudentGroupRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.request.StudentRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.ApiSignUpDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentGroupResponseDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.error.ApiError;
import com.gmail.maxsvynarchuk.presentation.security.AuthUser;
import com.gmail.maxsvynarchuk.presentation.security.serivce.UserPrincipal;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.service.StudentService;
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
