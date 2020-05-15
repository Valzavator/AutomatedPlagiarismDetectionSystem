package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskGroupDto;
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
