package com.autoplag.facade.impl;

import com.autoplag.facade.Facade;
import com.autoplag.facade.TaskGroupFacade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.*;
import com.autoplag.presentation.payload.request.TaskGroupPlagDetectionDto;
import com.autoplag.presentation.payload.response.*;
import com.autoplag.service.ProgrammingLanguageService;
import com.autoplag.service.TaskGroupService;
import com.autoplag.service.TaskService;
import lombok.AllArgsConstructor;

import java.util.List;

@Facade
@AllArgsConstructor
public class TaskGroupFacadeImpl implements TaskGroupFacade {
    private final TaskService taskService;
    private final TaskGroupService taskGroupService;
    private final ProgrammingLanguageService programmingLanguageService;

    private final Converter<TaskGroup, BasicTaskGroupDto> taskGroupToBasicTaskGroupDto;
    private final Converter<TaskGroup, TaskGroupDto> taskGroupToTaskGroupDto;
    private final Converter<Task, BasicTaskDto> taskToBasicTaskDto;
    private final Converter<ProgrammingLanguage, ProgrammingLanguageDto> programmingLanguageDto;
    private final Converter<TaskGroupPlagDetectionDto, PlagDetectionSettings> settingsConverter;

    @Override
    public TaskGroupDto getTaskGroupById(Long taskId, Long groupId) {
        TaskGroupKey taskGroupKey = new TaskGroupKey(taskId, groupId);
        TaskGroup taskGroup = taskGroupService.getTaskGroupById(taskGroupKey);
        return taskGroupToTaskGroupDto.convert(taskGroup);
    }

    @Override
    public void checkTaskGroupNow(Long taskId, Long groupId) {
        TaskGroupKey taskGroupKey = new TaskGroupKey(taskId, groupId);
        taskGroupService.checkTaskGroupNow(taskGroupKey);
    }

    @Override
    public void deleteTaskGroup(Long taskId, Long groupId) {
        TaskGroupKey taskGroupKey = new TaskGroupKey(taskId, groupId);
        taskGroupService.deleteTaskGroup(taskGroupKey);
    }

    @Override
    public OptionsForSettingsDto getOptionsForTaskGroupAdding(Long courseId, Long groupId) {
        List<ProgrammingLanguage> languages = programmingLanguageService.getAllProgrammingLanguages();
        List<ProgrammingLanguageDto> languageDtos = programmingLanguageDto.convertAll(languages);

        List<Task> tasks = taskService.getAllTasksByCourseIdAndNotAssignedToGroup(courseId, groupId);
        List<BasicTaskDto> taskDtos = taskToBasicTaskDto.convertAll(tasks);

        return new OptionsForSettingsDto(languageDtos, taskDtos);
    }

    @Override
    public BasicTaskGroupDto assignNewTaskGroup(TaskGroupPlagDetectionDto dto) {
        PlagDetectionSettings settings = settingsConverter.convert(dto);
        TaskGroup newTaskGroup = taskGroupService.assignNewTaskGroup(dto.getGroupId(),
                dto.getTaskId(),
                dto.getProgrammingLanguageId(),
                dto.getBaseCodeZip(),
                dto.getExpiryDate(),
                settings);
        return taskGroupToBasicTaskGroupDto.convert(newTaskGroup);
    }

}
