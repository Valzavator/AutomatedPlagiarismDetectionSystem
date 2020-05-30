package com.autoplag.facade;

import com.autoplag.presentation.payload.request.TaskGroupPlagDetectionDto;
import com.autoplag.presentation.payload.response.BasicTaskGroupDto;
import com.autoplag.presentation.payload.response.OptionsForSettingsDto;
import com.autoplag.presentation.payload.response.TaskGroupDto;


public interface TaskGroupFacade {

    TaskGroupDto getTaskGroupById(Long taskId, Long groupId);

    void checkTaskGroupNow(Long taskId, Long groupId);

    OptionsForSettingsDto getOptionsForTaskGroupAdding(Long courseId, Long groupId);

    BasicTaskGroupDto assignNewTaskGroup(TaskGroupPlagDetectionDto dto);

    void deleteTaskGroup(Long taskId, Long groupId);

}
