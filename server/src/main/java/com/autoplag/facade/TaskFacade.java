package com.autoplag.facade;

import com.autoplag.presentation.payload.request.TaskRequestDto;
import com.autoplag.presentation.payload.response.PagedDto;
import com.autoplag.presentation.payload.response.TaskDto;

public interface TaskFacade {

    PagedDto<TaskDto> getTasksForCourse(Long courseId,
                                        int page,
                                        int size);

    void addTaskToCourse(Long userId, TaskRequestDto dto);

}
