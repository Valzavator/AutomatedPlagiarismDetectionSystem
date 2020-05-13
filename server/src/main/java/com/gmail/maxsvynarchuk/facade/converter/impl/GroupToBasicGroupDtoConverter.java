package com.gmail.maxsvynarchuk.facade.converter.impl;

import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Group;
import com.gmail.maxsvynarchuk.presentation.payload.response.BasicGroupDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GroupToBasicGroupDtoConverter implements Converter<Group, BasicGroupDto> {
    private final ModelMapper mapper;

    @Override
    public BasicGroupDto convert(Group group) {
        return mapper.map(group, BasicGroupDto.class);
    }
}
