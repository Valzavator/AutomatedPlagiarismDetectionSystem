package com.autoplag.presentation.controller;

import com.autoplag.facade.GroupFacade;
import com.autoplag.presentation.payload.request.GroupRequestDto;
import com.autoplag.presentation.payload.response.BasicGroupDto;
import com.autoplag.presentation.payload.response.GroupDto;
import com.autoplag.presentation.security.AuthUser;
import com.autoplag.presentation.security.serivce.UserPrincipal;
import com.autoplag.presentation.util.ControllerUtil;
import com.autoplag.service.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/api/v1/courses/{courseId}/groups")
@AllArgsConstructor
@Slf4j
public class GroupController {
    private final GroupService groupService;
    private final GroupFacade groupFacade;

    @GetMapping("/{groupId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getGroup(
            @PathVariable(value = "groupId") long groupId) {
        GroupDto groupDto = groupFacade.getGroupById(groupId);
        return ResponseEntity.ok().body(groupDto);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addGroupToCourse(
            @AuthUser UserPrincipal user,
            @Valid @RequestBody GroupRequestDto dto) {
        BasicGroupDto groupDto = groupFacade.addGroupToCourse(user.getId(), dto);
        URI location = ControllerUtil.getLocation(
                "api/v1/courses", dto.getCourseId().toString(),
                "groups", groupDto.getId().toString());
        return ResponseEntity.created(location).body(groupDto);
    }

    @PostMapping("/{groupId}/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteGroupFromCourse(
            @PathVariable(value = "courseId") long courseId,
            @PathVariable(value = "groupId") long groupId) {
        groupService.deleteGroupFromCourse(groupId, courseId);
        return ResponseEntity.noContent().build();
    }

}
