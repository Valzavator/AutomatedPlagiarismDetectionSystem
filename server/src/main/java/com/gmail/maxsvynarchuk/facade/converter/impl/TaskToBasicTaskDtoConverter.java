package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import com.gmail.maxsvynarchuk.presentation.payload.response.BasicTaskDto;
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
