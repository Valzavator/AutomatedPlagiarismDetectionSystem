package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Group;
import com.gmail.maxsvynarchuk.presentation.payload.request.GroupRequestDto;
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
