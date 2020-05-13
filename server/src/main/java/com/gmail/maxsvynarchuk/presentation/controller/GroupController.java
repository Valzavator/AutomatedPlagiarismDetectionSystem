package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.GroupFacade;
import com.gmail.maxsvynarchuk.presentation.payload.response.BasicGroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.GroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
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
@RequestMapping("/api/v1/courses/{courseId}/groups")
@AllArgsConstructor
@Slf4j
public class GroupController {
    private GroupFacade groupFacade;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PagedDto<BasicGroupDto>> getAllGroupsForCourse(
            @PathVariable("courseId") Long courseId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "6") int size) {
        PagedDto<BasicGroupDto> pagedDto = groupFacade.getGroupsByCourseIdAndCreatorId(
                courseId, page, size);
        return ResponseEntity.ok()
                .body(pagedDto);
    }

    @GetMapping("/{groupId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllGroupsForCourse(
            @PathVariable(value = "groupId") long groupId,
            HttpServletRequest request) {
        Optional<GroupDto> courseDtoOpt = groupFacade.getGroupById( groupId);
        if (courseDtoOpt.isPresent()) {
            return ResponseEntity.ok()
                    .body(courseDtoOpt.get());
        }
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}
