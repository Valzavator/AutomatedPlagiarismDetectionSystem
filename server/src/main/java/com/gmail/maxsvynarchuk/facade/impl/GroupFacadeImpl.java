package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.GroupFacade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Course;
import com.gmail.maxsvynarchuk.persistence.domain.Group;
import com.gmail.maxsvynarchuk.presentation.exception.BadRequestException;
import com.gmail.maxsvynarchuk.presentation.exception.ResourceNotFoundException;
import com.gmail.maxsvynarchuk.presentation.payload.request.GroupRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.BasicGroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.GroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.service.CourseService;
import com.gmail.maxsvynarchuk.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Facade
@AllArgsConstructor
public class GroupFacadeImpl implements GroupFacade {
    private final CourseService courseService;
    private final GroupService groupService;

    private final Converter<GroupRequestDto, Group> groupRequestDtoToGroup;
    private final Converter<Group, BasicGroupDto> groupToBasicGroupDto;
    private final Converter<Group, GroupDto> groupToGroupDto;


    @Override
    public PagedDto<BasicGroupDto> getGroupsByCourseId(Long courseId,
                                                       int page,
                                                       int size) {
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
        Group group = groupService.getGroupById(courseId)
                .orElseThrow(ResourceNotFoundException::new);
        return groupToGroupDto.convert(group);
    }

    @Override
    public BasicGroupDto addGroupToCourse(Long userId, GroupRequestDto dto) {
        Course course = courseService.getCourseById(userId, dto.getCourseId())
                .orElseThrow(BadRequestException::new);
        Group group = groupRequestDtoToGroup.convert(dto);
        group.setCourse(course);
        group = groupService.saveGroup(group);
        return groupToBasicGroupDto.convert(group);
    }

}
