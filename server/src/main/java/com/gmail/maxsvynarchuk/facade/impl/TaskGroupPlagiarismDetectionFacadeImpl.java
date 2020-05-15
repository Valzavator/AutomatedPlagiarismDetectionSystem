package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.config.constant.JPlag;
import com.gmail.maxsvynarchuk.config.constant.Paths;
import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.SingleCheckPlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.facade.TaskGroupPlagiarismDetectionFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionResult;
import com.gmail.maxsvynarchuk.persistence.domain.PlagDetectionSettings;
import com.gmail.maxsvynarchuk.persistence.domain.ProgrammingLanguage;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import com.gmail.maxsvynarchuk.presentation.payload.request.SingleCheckPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.*;
import com.gmail.maxsvynarchuk.service.PlagDetectionResultService;
import com.gmail.maxsvynarchuk.service.PlagiarismDetectionService;
import com.gmail.maxsvynarchuk.service.ProgrammingLanguageService;
import com.gmail.maxsvynarchuk.service.TaskService;
import com.gmail.maxsvynarchuk.util.FileSystemWriter;
import lombok.AllArgsConstructor;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Facade
@AllArgsConstructor
public class TaskGroupPlagiarismDetectionFacadeImpl implements TaskGroupPlagiarismDetectionFacade {
    private final ProgrammingLanguageService programmingLanguageService;
    private final TaskService taskService;
    private final PlagiarismDetectionService plagiarismDetectionService;
    private final PlagDetectionResultService plagDetectionResultService;

    private final Converter<ProgrammingLanguage, ProgrammingLanguageDto> programmingLanguageDto;
    private final Converter<Task, BasicTaskDto> taskToBasicTaskDto;
    private final Converter<SingleCheckPlagDetectionDto, PlagDetectionSettings> settingsConverter;
    private final Converter<PlagDetectionResult, SingleCheckPlagDetectionResultDto> resultConverter;

    @Override
    public OptionsForSettingsDto getOptionsForSettings(Long courseId) {
        List<ProgrammingLanguage> languages = programmingLanguageService.getAllProgrammingLanguages();
        List<ProgrammingLanguageDto> languageDtos = programmingLanguageDto.convertAll(languages);

        List<Task> tasks = taskService.getAllTasksByCourseId(courseId);
        List<BasicTaskDto> taskDtos = taskToBasicTaskDto.convertAll(tasks);

        return new OptionsForSettingsDto(languageDtos, taskDtos);
    }

}
