package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Task;
import com.autoplag.presentation.payload.request.TaskRequestDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TaskRequestDtoToTaskConverter implements Converter<TaskRequestDto, Task> {
    private final ModelMapper mapper;

    @Override
    public Task convert(TaskRequestDto taskRequestDto) {
        mapper.typeMap(TaskRequestDto.class, Task.class).addMappings(mp -> mp.skip(Task::setId));
        return mapper.map(taskRequestDto, Task.class);
    }
}
