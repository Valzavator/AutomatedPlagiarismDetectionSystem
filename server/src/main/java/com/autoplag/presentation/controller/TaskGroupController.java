package com.autoplag.presentation.controller;

import com.autoplag.facade.TaskGroupFacade;
import com.autoplag.presentation.payload.request.TaskGroupPlagDetectionDto;
import com.autoplag.presentation.payload.response.BasicTaskGroupDto;
import com.autoplag.presentation.payload.response.OptionsForSettingsDto;
import com.autoplag.presentation.payload.response.TaskGroupDto;
import com.autoplag.presentation.util.ControllerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;


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
            @PathVariable(value = "groupId") long groupId) {
        TaskGroupDto taskGroupDto = taskGroupFacade.getTaskGroupById(taskId, groupId);
        return ResponseEntity.ok().body(taskGroupDto);
    }

    @PostMapping(value = "/{taskId}/check-now")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> checkNow(
            @PathVariable(value = "taskId") Long taskId,
            @PathVariable(value = "groupId") Long groupId) {
        taskGroupFacade.checkTaskGroupNow(taskId, groupId);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<?> assignNewTaskGroup(@PathVariable(value = "courseId") Long courseId,
                                                @Valid TaskGroupPlagDetectionDto dto)
            throws HttpMediaTypeNotSupportedException {
        if (Objects.nonNull(dto.getBaseCodeZip()) &&
                dto.getBaseCodeZip().getSize() <= 0) {
            dto.setBaseCodeZip(null);
        } else if (Objects.nonNull(dto.getBaseCodeZip()) &&
                !ControllerUtil.validateZipMediaType(dto.getBaseCodeZip())) {
            throw new HttpMediaTypeNotSupportedException("Invalid 'baseCodeZip' media type!");
        }
        BasicTaskGroupDto responseDto = taskGroupFacade.assignNewTaskGroup(dto);
        URI location = ControllerUtil.getLocation("api/v1/courses", courseId.toString(),
                "groups", dto.getGroupId().toString(),
                "tasks", dto.getTaskId().toString());
        return ResponseEntity.created(location).body(responseDto);
    }

    @PostMapping("/{taskId}/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteTaskGroup(
            @PathVariable(value = "taskId") long taskId,
            @PathVariable(value = "groupId") long groupId) {
        taskGroupFacade.deleteTaskGroup(taskId, groupId);
        return ResponseEntity.noContent().build();
    }
}
