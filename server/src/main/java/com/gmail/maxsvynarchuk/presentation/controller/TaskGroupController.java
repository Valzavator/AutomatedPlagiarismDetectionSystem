package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.TaskGroupFacade;
import com.gmail.maxsvynarchuk.facade.TaskGroupPlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSettingsDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskGroupDto;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/courses/{courseId}/groups/{groupId}/tasks")
@AllArgsConstructor
@Slf4j
public class TaskGroupController {
    private final TaskGroupFacade taskGroupFacade;

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getGroup(
            @PathVariable(value = "taskId") long taskId,
            @PathVariable(value = "groupId") long groupId,
            HttpServletRequest request) {
        Optional<TaskGroupDto> taskGroupDtoOpt = taskGroupFacade.getTaskGroupById(taskId, groupId);
        return ControllerUtil.prepareResponse(taskGroupDtoOpt, request.getRequestURI());
    }

}
