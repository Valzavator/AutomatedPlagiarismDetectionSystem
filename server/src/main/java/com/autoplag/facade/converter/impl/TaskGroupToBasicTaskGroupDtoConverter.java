package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.TaskGroup;
import com.autoplag.presentation.payload.response.BasicTaskGroupDto;
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
