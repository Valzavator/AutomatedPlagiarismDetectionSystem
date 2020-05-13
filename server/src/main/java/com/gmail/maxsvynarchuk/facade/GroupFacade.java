package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.response.BasicGroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.GroupDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;

import java.util.Optional;

public interface GroupFacade {

    PagedDto<BasicGroupDto> getGroupsByCourseIdAndCreatorId(Long courseId,
                                                            int page,
                                                            int size);

    Optional<GroupDto> getGroupById(Long courseId);

}
