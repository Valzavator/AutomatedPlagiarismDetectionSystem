package com.gmail.maxsvynarchuk.facade.impl;

import com.gmail.maxsvynarchuk.facade.TaskFacade;
import com.gmail.maxsvynarchuk.facade.Facade;
import com.gmail.maxsvynarchuk.facade.converter.Converter;
import com.gmail.maxsvynarchuk.persistence.domain.Task;
import com.gmail.maxsvynarchuk.presentation.payload.response.PagedDto;
import com.gmail.maxsvynarchuk.presentation.payload.response.TaskDto;
import com.gmail.maxsvynarchuk.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Facade
@AllArgsConstructor
public class TaskFacadeImpl implements TaskFacade {
    private final TaskService taskService;

    private final Converter<Task, TaskDto> taskToTaskDto;

    @Override
    public PagedDto<TaskDto> getTasksForCourse(Long courseId, int page, int size) {
        Page<Task> taskPagePage = taskService.getAllTasksByCourseId(courseId, page, size);
        List<TaskDto> taskDtos = taskToTaskDto.convertAll(taskPagePage.getContent());
        return PagedDto.<TaskDto>builder()
                .content(taskDtos)
                .page(taskPagePage.getNumber())
                .size(taskPagePage.getSize())
                .totalElements(taskPagePage.getTotalElements())
                .totalPages(taskPagePage.getTotalPages())
                .build();
    }
}
