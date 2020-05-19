package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.GroupFacade;
import com.gmail.maxsvynarchuk.presentation.payload.response.GroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.error.ApiError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/courses/{courseId}/groups/{groupId}")
@AllArgsConstructor
@Slf4j
public class GroupController {
    private final GroupFacade groupFacade;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getGroup(
            @PathVariable(value = "groupId") long groupId,
            HttpServletRequest request) {
        Optional<GroupDto> groupDtoOpt = groupFacade.getGroupById(groupId);
        if (groupDtoOpt.isPresent()) {
            return ResponseEntity.ok()
                    .body(groupDtoOpt.get());
        }
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}
