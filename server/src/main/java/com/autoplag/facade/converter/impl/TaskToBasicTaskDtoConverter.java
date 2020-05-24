package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Task;
import com.autoplag.presentation.payload.response.BasicTaskDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TaskToBasicTaskDtoConverter implements Converter<Task, BasicTaskDto> {
    private final ModelMapper mapper;

    @Override
    public BasicTaskDto convert(Task task) {
        return mapper.map(task, BasicTaskDto.class);
    }
}
