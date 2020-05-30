package com.autoplag.facade.impl;

import com.autoplag.facade.Facade;
import com.autoplag.facade.GroupFacade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Group;
import com.autoplag.presentation.payload.request.GroupRequestDto;
import com.autoplag.presentation.payload.response.BasicGroupDto;
import com.autoplag.presentation.payload.response.GroupDto;
import com.autoplag.presentation.payload.response.PagedDto;
import com.autoplag.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Facade
@AllArgsConstructor
public class GroupFacadeImpl implements GroupFacade {
    private final GroupService groupService;

    private final Converter<GroupRequestDto, Group> groupRequestDtoToGroup;
    private final Converter<Group, BasicGroupDto> groupToBasicGroupDto;
    private final Converter<Group, GroupDto> groupToGroupDto;


    @Override
    public PagedDto<BasicGroupDto> getGroupsByCourseId(Long courseId, int page, int size) {
        Page<Group> groupsPage =
                groupService.getAllGroupsByCourseId(courseId, page, size);
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
    public GroupDto getGroupById(Long courseId) {
        Group group = groupService.getGroupById(courseId);
        return groupToGroupDto.convert(group);
    }

    @Override
    public BasicGroupDto addGroupToCourse(Long creatorId, GroupRequestDto dto) {
        Group group = groupRequestDtoToGroup.convert(dto);
        group = groupService.saveGroup(creatorId, dto.getCourseId(), group);
        return groupToBasicGroupDto.convert(group);
    }

}
