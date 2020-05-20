package com.gmail.maxsvynarchuk.facade;

import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskDto;

public interface TaskFacade {

    PagedDto<TaskDto> getTasksForCourse(Long courseId,
                                        int page,
                                        int size);

}
