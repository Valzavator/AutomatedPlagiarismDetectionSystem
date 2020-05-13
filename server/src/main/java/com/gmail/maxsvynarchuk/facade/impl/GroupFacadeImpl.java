package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.GroupFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Group;
import com.gmail.maxsvynarchuk.presentation.payload.response.BasicGroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.GroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Facade
@AllArgsConstructor
public class GroupFacadeImpl implements GroupFacade {
    private final GroupService groupService;

    private final Converter<Group, BasicGroupDto> groupToBasicGroupDto;
    private final Converter<Group, GroupDto> groupToGroupDto;


    @Override
    public PagedDto<BasicGroupDto> getGroupsByCourseIdAndCreatorId(Long courseId,
                                                                   int page,
                                                                   int size) {
        Page<Group> groupsPage =
                groupService.getGroupsByCourseId(courseId, page, size);
        List<BasicGroupDto> courseDtos = groupToBasicGroupDto.convertAll(groupsPage.getContent());
        return PagedDto.<BasicGroupDto>builder()
                .content(courseDtos)
                .page(groupsPage.getNumber())
                .size(groupsPage.getSize())
                .totalElements(groupsPage.getTotalElements())
                .totalPages(groupsPage.getTotalPages())
                .build();
    }

    @Override
    public Optional<GroupDto> getGroupById(Long courseId) {
        Optional<Group> groupOpt = groupService.getGroupById(courseId);
        return groupOpt.map(groupToGroupDto::convert);
    }

}
