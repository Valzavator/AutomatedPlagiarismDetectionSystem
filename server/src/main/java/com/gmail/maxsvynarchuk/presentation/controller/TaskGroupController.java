package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.TaskGroupFacade;
import com.gmail.maxsvynarchuk.presentation.payload.request.TaskGroupPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSettingsDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskGroupDto;
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
@RequestMapping("/api/v1/courses/{courseId}/groups/{groupId}/tasks")
@AllArgsConstructor
@Slf4j
public class TaskGroupController {
    private final TaskGroupFacade taskGroupFacade;

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTaskGroup(
            @PathVariable(value = "taskId") long taskId,
            @PathVariable(value = "groupId") long groupId,
            HttpServletRequest request) {
        Optional<TaskGroupDto> taskGroupDtoOpt = taskGroupFacade.getTaskGroupById(taskId, groupId);
        return ControllerUtil.prepareResponse(taskGroupDtoOpt, request.getRequestURI());
    }

    @GetMapping("/options")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getOptionsForTaskGroupSettings(
            @PathVariable(value = "courseId") long courseId,
            @PathVariable(value = "groupId") long groupId) {
        OptionsForSettingsDto optionsForSettings =
                taskGroupFacade.getOptionsForTaskGroupAdding(courseId, groupId);
        return ResponseEntity.ok().body(optionsForSettings);
    }

    @PostMapping(value = "/assign", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> assignNewTaskGroup(
            @AuthUser UserPrincipal currentUser,
            @PathVariable(value = "groupId") long groupId,
            @Valid TaskGroupPlagDetectionDto dto) throws HttpMediaTypeNotSupportedException {
        if (Objects.nonNull(dto.getBaseCodeZip()) &&
                dto.getBaseCodeZip().getSize() <= 0) {
            dto.setBaseCodeZip(null);
        } else if (Objects.nonNull(dto.getBaseCodeZip()) &&
                !ControllerUtil.validateZipMediaType(dto.getBaseCodeZip())) {
            throw new HttpMediaTypeNotSupportedException("Invalid 'baseCodeZip' media type!");
        }
        taskGroupFacade.assignNewTaskGroup(groupId, dto);
        return ResponseEntity.ok().build();
    }
}
