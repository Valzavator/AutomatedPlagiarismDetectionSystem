package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.response.TaskGroupDto;

import java.util.Optional;

public interface TaskGroupFacade {

    Optional<TaskGroupDto> getTaskGroupById(Long taskId, Long groupId);

}
