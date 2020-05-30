package com.autoplag.service.impl;

import com.autoplag.config.constant.JPlag;
import com.autoplag.config.constant.Paths;
import com.autoplag.persistence.dao.TaskGroupDao;
import com.autoplag.persistence.domain.*;
import com.autoplag.persistence.domain.type.PlagDetectionStatus;
import com.autoplag.service.GroupService;
import com.autoplag.service.ProgrammingLanguageService;
import com.autoplag.service.TaskGroupService;
import com.autoplag.service.TaskService;
import com.autoplag.service.exception.BadRequestException;
import com.autoplag.service.exception.ResourceNotFoundException;
import com.autoplag.util.FileSystemWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TaskGroupServiceImpl implements TaskGroupService {
    private final GroupService groupService;
    private final TaskService taskService;
    private final ProgrammingLanguageService programmingLanguageService;
    private final TaskGroupDao taskGroupDao;
    private final FileSystemWriter fileSystemWriter;

    @Transactional(readOnly = true)
    @Override
    public TaskGroup getTaskGroupById(TaskGroupKey id) {
        return taskGroupDao.findOne(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void checkTaskGroupNow(TaskGroupKey id) {
        TaskGroup taskGroup = getTaskGroupById(id);
        if (taskGroup.getPlagDetectionStatus() == PlagDetectionStatus.IN_PROCESS) {
            throw new BadRequestException();
        }
        taskGroup.setPlagDetectionStatus(PlagDetectionStatus.PENDING);
        taskGroup.setExpiryDate(new Date());
        saveTaskGroup(taskGroup);
    }

    @Override
    public Optional<TaskGroup> getTaskGroupByIdAndStatus(TaskGroupKey id, PlagDetectionStatus status) {
        return taskGroupDao.findByIdAndStatus(id, status);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskGroup> getAllExpiredTaskGroupsWithPendingStatus() {
        return taskGroupDao.findAllExpiredTaskGroupWithPendingStatus();
    }

    @Override
    public TaskGroup saveTaskGroup(TaskGroup taskGroup) {
        return taskGroupDao.save(taskGroup);
    }

    @Override
    public void deleteTaskGroup(TaskGroupKey id) {
        Optional<TaskGroup> taskGroupOpt = taskGroupDao.findOne(id);
        if (taskGroupOpt.isPresent()) {
            if (taskGroupOpt.get().getPlagDetectionStatus() == PlagDetectionStatus.IN_PROCESS) {
                throw new BadRequestException();
            }
            taskGroupDao.deleteById(id);
        }
    }

    @Override
    public TaskGroup assignNewTaskGroup(Long groupId,
                                        Long taskId,
                                        Integer languageId,
                                        MultipartFile baseCodeZip,
                                        Date expiryDate,
                                        PlagDetectionSettings settings) {
        Group group = groupService.getGroupById(groupId);
        Task task = taskService.getTaskById(taskId);
        ProgrammingLanguage programmingLanguage = programmingLanguageService
                .getProgrammingLanguageById(languageId);

        settings.setProgrammingLanguage(programmingLanguage);

        generatePaths(settings, group, task, Objects.nonNull(baseCodeZip));

        PlagDetectionResult result = null;
        if (Objects.nonNull(baseCodeZip)) {
            fileSystemWriter.deleteDirectory(settings.getBaseCodePath());
            if (!fileSystemWriter.unzipFile(baseCodeZip, settings.getBaseCodePath())) {
                result = PlagDetectionResult.failed("Unable to unpack archive with base code to plagiarism detection!");
            }
        }

        TaskGroupKey id = new TaskGroupKey(taskId, groupId);
        TaskGroup taskGroup = TaskGroup.builder()
                .id(id)
                .task(task)
                .creationDate(new Date())
                .expiryDate(expiryDate)
                .plagDetectionStatus(Objects.isNull(result) ? PlagDetectionStatus.PENDING : PlagDetectionStatus.FAILED)
                .plagDetectionSettings(settings)
                .plagDetectionResult(result)
                .build();
        return saveTaskGroup(taskGroup);
    }

    private void generatePaths(PlagDetectionSettings settings,
                               Group group,
                               Task task,
                               boolean isBaseCodeZipExists) {
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
        if (isBaseCodeZipExists) {
            settings.setBaseCodePath(
                    Path.of(Paths.REPOSITORIES_DATA_FOLDER, taskPath, JPlag.BASE_CODE_DIRECTORY).toString());
        }
    }

}
