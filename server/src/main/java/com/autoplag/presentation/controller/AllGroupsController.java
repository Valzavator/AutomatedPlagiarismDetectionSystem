package com.autoplag.presentation.controller;

import com.autoplag.facade.GroupFacade;
import com.autoplag.presentation.payload.response.BasicGroupDto;
import com.autoplag.presentation.payload.response.PagedDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/groups")
@AllArgsConstructor
@Slf4j
public class AllGroupsController {
    private final GroupFacade groupFacade;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PagedDto<BasicGroupDto>> getAllGroupsForCourse(
            @PathVariable("courseId") Long courseId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "6") int size) {
        PagedDto<BasicGroupDto> pagedDto = groupFacade.getGroupsByCourseId(
                courseId, page, size);
        return ResponseEntity.ok()
                .body(pagedDto);
    }

}
