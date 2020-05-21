package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.request.TaskGroupPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSettingsDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskGroupDto;

import java.util.Optional;

public interface TaskGroupFacade {

    Optional<TaskGroupDto> getTaskGroupById(Long taskId, Long groupId);

    OptionsForSettingsDto getOptionsForTaskGroupAdding(Long courseId, Long groupId);

    void assignNewTaskGroup(TaskGroupPlagDetectionDto dto);

    boolean deleteTaskGroup(Long taskId, Long groupId);

}
