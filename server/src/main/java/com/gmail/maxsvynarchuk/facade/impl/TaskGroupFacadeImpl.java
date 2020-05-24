package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.config.constant.JPlag;
import com.gmail.maxsvynarchuk.config.constant.Paths;
import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.TaskGroupFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.*;
import com.gmail.maxsvynarchuk.persistence.domain.type.PlagDetectionStatus;
import com.gmail.maxsvynarchuk.presentation.exception.BadRequestException;
import com.gmail.maxsvynarchuk.presentation.exception.ResourceNotFoundException;
import com.gmail.maxsvynarchuk.presentation.payload.request.TaskGroupPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.*;
import com.gmail.maxsvynarchuk.service.GroupService;
import com.gmail.maxsvynarchuk.service.ProgrammingLanguageService;
import com.gmail.maxsvynarchuk.service.TaskGroupService;
import com.gmail.maxsvynarchuk.service.TaskService;
import com.gmail.maxsvynarchuk.util.FileSystemWriter;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Facade
@AllArgsConstructor
public class TaskGroupFacadeImpl implements TaskGroupFacade {
    private final GroupService groupService;
    private final TaskService taskService;
    private final TaskGroupService taskGroupService;
    private final ProgrammingLanguageService programmingLanguageService;

    private final FileSystemWriter fileSystemWriter;

    private final Converter<TaskGroup, BasicTaskGroupDto> taskGroupToBasicTaskGroupDto;
    private final Converter<TaskGroup, TaskGroupDto> taskGroupToTaskGroupDto;
    private final Converter<Task, BasicTaskDto> taskToBasicTaskDto;
    private final Converter<ProgrammingLanguage, ProgrammingLanguageDto> programmingLanguageDto;
    private final Converter<TaskGroupPlagDetectionDto, PlagDetectionSettings> settingsConverter;

    @Override
    public TaskGroupDto getTaskGroupById(Long taskId, Long groupId) {
        TaskGroupKey taskGroupKey = new TaskGroupKey(taskId, groupId);
        TaskGroup taskGroup = taskGroupService.getTaskGroupById(taskGroupKey)
                .orElseThrow(ResourceNotFoundException::new);
        return taskGroupToTaskGroupDto.convert(taskGroup);
    }

    @Override
    public void checkTaskGroupNow(Long taskId, Long groupId) {
        TaskGroupKey taskGroupKey = new TaskGroupKey(taskId, groupId);
        taskGroupService.checkTaskGroupNow(taskGroupKey);
    }

    @Override
    public boolean deleteTaskGroup(Long taskId, Long groupId) {
        TaskGroupKey taskGroupKey = new TaskGroupKey(taskId, groupId);
        return taskGroupService.deleteTaskGroup(taskGroupKey);
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
        Group group = groupService.getGroupById(dto.getGroupId())
                .orElseThrow(BadRequestException::new);
        Task task = taskService.getTaskById(dto.getTaskId())
                .orElseThrow(BadRequestException::new);
        ProgrammingLanguage programmingLanguage = programmingLanguageService
                .getProgrammingLanguageById(dto.getProgrammingLanguageId())
                .orElseThrow(BadRequestException::new);
        PlagDetectionSettings settings = settingsConverter.convert(dto);
        settings.setProgrammingLanguage(programmingLanguage);

        generatePaths(settings, group, task, dto.getBaseCodeZip());

        PlagDetectionResult result = null;
        if (Objects.nonNull(dto.getBaseCodeZip())) {
            fileSystemWriter.deleteDirectory(settings.getBaseCodePath());
            if (!fileSystemWriter.unzipFile(dto.getBaseCodeZip(), settings.getBaseCodePath())) {
                result = PlagDetectionResult.failed("Unable to unpack archive with base code to plagiarism detection!");
            }
        }

        TaskGroupKey id = new TaskGroupKey(dto.getTaskId(), dto.getGroupId());
        TaskGroup taskGroup = TaskGroup.builder()
                .id(id)
                .creationDate(new Date())
                .expiryDate(dto.getExpiryDate())
                .plagDetectionStatus(Objects.isNull(result) ? PlagDetectionStatus.PENDING : PlagDetectionStatus.FAILED)
                .plagDetectionSettings(settings)
                .plagDetectionResult(result)
                .build();

        TaskGroup newTaskGroup = taskGroupService.saveTaskGroup(taskGroup);
        newTaskGroup.setTask(task);
        return taskGroupToBasicTaskGroupDto.convert(newTaskGroup);
    }

    private void generatePaths(PlagDetectionSettings settings,
                               Group group,
                               Task task,
                               MultipartFile baseCodeZip) {
        Course course = task.getCourse();
        User user = course.getCreator();
        String taskPath = Path.of(
                user.getId().toString(),
                course.getName(),
                group.getName(),
                task.getName()
        ).toString();
        settings.setDataPath(
                Path.of(Paths.REPOSITORIES_DATA_FOLDER, taskPath).toString());
        settings.setResultPath(
                Path.of(Paths.ANALYSIS_RESULT_FOLDER, taskPath).toString());
        if (Objects.nonNull(baseCodeZip)) {
            settings.setBaseCodePath(
                    Path.of(Paths.REPOSITORIES_DATA_FOLDER, taskPath, JPlag.BASE_CODE_DIRECTORY).toString());
        }
    }
}
