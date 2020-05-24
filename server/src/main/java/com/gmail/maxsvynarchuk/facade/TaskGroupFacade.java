package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.request.TaskGroupPlagDetectionDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.BasicTaskGroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.OptionsForSettingsDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskGroupDto;


public interface TaskGroupFacade {

    TaskGroupDto getTaskGroupById(Long taskId, Long groupId);

    void checkTaskGroupNow(Long taskId, Long groupId);

    OptionsForSettingsDto getOptionsForTaskGroupAdding(Long courseId, Long groupId);

    BasicTaskGroupDto assignNewTaskGroup(TaskGroupPlagDetectionDto dto);

    boolean deleteTaskGroup(Long taskId, Long groupId);

}
