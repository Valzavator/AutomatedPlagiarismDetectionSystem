package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.TaskGroupFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroupKey;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskGroupDto;
import com.gmail.maxsvynarchuk.service.TaskGroupService;
import lombok.AllArgsConstructor;

import java.util.Optional;

@Facade
@AllArgsConstructor
public class TaskGroupFacadeImpl implements TaskGroupFacade {
    private final TaskGroupService taskGroupService;

    private final Converter<TaskGroup, TaskGroupDto> taskGroupToTaskGroupDto;

    @Override
    public Optional<TaskGroupDto> getTaskGroupById(Long taskId, Long groupId) {
        TaskGroupKey taskGroupKey = new TaskGroupKey(taskId, groupId);
        Optional<TaskGroup> taskGroupOpt = taskGroupService.getTaskGroupById(taskGroupKey);
        return taskGroupOpt.map(taskGroupToTaskGroupDto::convert);
    }
}
