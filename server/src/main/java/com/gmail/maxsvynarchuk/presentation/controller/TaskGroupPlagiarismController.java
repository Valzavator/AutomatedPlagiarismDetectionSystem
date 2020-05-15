package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.SingleCheckPlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.facade.TaskGroupPlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSettingsDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSingleCheckSettingsDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.SingleCheckPlagDetectionResultDto;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses/{courseId}")
@AllArgsConstructor
@Slf4j
public class TaskGroupPlagiarismController {
    private final TaskGroupPlagiarismDetectionFacade taskGroupPlagiarismDetectionFacade;

    @GetMapping("/options")
    public ResponseEntity<?> getOptionsForTaskGroupSettings(
            @PathVariable(value = "courseId") long courseId) {
        OptionsForSettingsDto optionsForSettings =
                taskGroupPlagiarismDetectionFacade.getOptionsForSettings(courseId);
        return ResponseEntity.ok().body(optionsForSettings);
    }

}
