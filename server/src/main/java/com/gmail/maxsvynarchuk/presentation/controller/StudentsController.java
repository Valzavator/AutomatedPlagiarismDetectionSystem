package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.StudentFacade;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.StudentDto;
import com.gmail.maxsvynarchuk.presentation.security.AuthUser;
import com.gmail.maxsvynarchuk.presentation.security.serivce.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
@Slf4j
public class StudentsController {
    private final StudentFacade studentFacade;

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

}
