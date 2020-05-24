package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.TaskGroup;
import com.autoplag.presentation.payload.response.TaskGroupDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor
@Component
public class TaskGroupToTaskGroupDtoConverter implements Converter<TaskGroup, TaskGroupDto> {
    private final ModelMapper mapper;

    @Override
    public TaskGroupDto convert(TaskGroup taskGroup) {
        TaskGroupDto taskGroupDto = mapper.map(taskGroup, TaskGroupDto.class);
        taskGroupDto.getPlagDetectionSettings().setBaseCodePresent(
                Objects.nonNull(taskGroup.getPlagDetectionSettings().getBaseCodePath()));
        return taskGroupDto;
    }
}
