package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.TaskGroup;
import com.gmail.maxsvynarchuk.presentation.payload.response.BasicTaskGroupDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TaskGroupToBasicTaskGroupDtoConverter implements Converter<TaskGroup, BasicTaskGroupDto> {
    private final ModelMapper mapper;

    @Override
    public BasicTaskGroupDto convert(TaskGroup taskGroup) {
        return mapper.map(taskGroup, BasicTaskGroupDto.class);
    }
}
