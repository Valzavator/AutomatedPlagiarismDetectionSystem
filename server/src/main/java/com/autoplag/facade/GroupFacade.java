package com.autoplag.facade;

import com.autoplag.presentation.payload.request.GroupRequestDto;
import com.autoplag.presentation.payload.response.BasicGroupDto;
import com.autoplag.presentation.payload.response.GroupDto;
import com.autoplag.presentation.payload.response.PagedDto;

public interface GroupFacade {

    PagedDto<BasicGroupDto> getGroupsByCourseId(Long courseId, int page, int size);

    GroupDto getGroupById(Long groupId);

    BasicGroupDto addGroupToCourse(Long creatorId, GroupRequestDto dto);

}
