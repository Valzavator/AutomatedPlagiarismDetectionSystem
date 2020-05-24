package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.request.GroupRequestDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.BasicGroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.GroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;

public interface GroupFacade {

    PagedDto<BasicGroupDto> getGroupsByCourseId(Long courseId,
                                                int page,
                                                int size);

    GroupDto getGroupById(Long groupId);

    BasicGroupDto addGroupToCourse(Long userId, GroupRequestDto dto);

}
