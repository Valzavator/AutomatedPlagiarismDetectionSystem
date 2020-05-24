package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Task;
import com.autoplag.presentation.payload.response.TaskDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TaskToTaskDtoConverter implements Converter<Task, TaskDto> {
    private final ModelMapper mapper;

    @Override
    public TaskDto convert(Task task) {
        return mapper.map(task, TaskDto.class);
    }
}
