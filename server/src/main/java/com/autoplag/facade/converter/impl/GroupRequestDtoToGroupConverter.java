package com.autoplag.facade.converter.impl;

import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Group;
import com.autoplag.presentation.payload.request.GroupRequestDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class GroupRequestDtoToGroupConverter implements Converter<GroupRequestDto, Group> {
    private final ModelMapper mapper;

    @Override
    public Group convert(GroupRequestDto groupRequestDto) {
        mapper.typeMap(GroupRequestDto.class, Group.class).addMappings(mp -> mp.skip(Group::setId));
        return mapper.map(groupRequestDto, Group.class);
    }
}
