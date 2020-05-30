package com.autoplag.service;

import com.autoplag.persistence.domain.PlagDetectionSettings;
import com.autoplag.persistence.domain.TaskGroup;
import com.autoplag.persistence.domain.TaskGroupKey;
import com.autoplag.persistence.domain.type.PlagDetectionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TaskGroupService {

    TaskGroup getTaskGroupById(TaskGroupKey id);

    void checkTaskGroupNow(TaskGroupKey id);

    Optional<TaskGroup> getTaskGroupByIdAndStatus(TaskGroupKey id, PlagDetectionStatus status);

    List<TaskGroup> getAllExpiredTaskGroupsWithPendingStatus();

    TaskGroup saveTaskGroup(TaskGroup taskGroup);

    void deleteTaskGroup(TaskGroupKey id);

    TaskGroup assignNewTaskGroup(Long groupId,
                                 Long taskId,
                                 Integer languageId,
                                 MultipartFile baseCodeZip,
                                 Date expiryDate,
                                 PlagDetectionSettings settings);

}
