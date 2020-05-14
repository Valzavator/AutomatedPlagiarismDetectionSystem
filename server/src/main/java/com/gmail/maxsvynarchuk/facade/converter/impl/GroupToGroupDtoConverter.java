package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Group;
import com.gmail.maxsvynarchuk.presentation.payload.response.GroupDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class GroupToGroupDtoConverter implements Converter<Group, GroupDto> {
    private final ModelMapper mapper;

    @Override
    public GroupDto convert(Group group) {
        return mapper.map(group, GroupDto.class);
    }
}
