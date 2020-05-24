package com.autoplag.facade.impl;

import com.autoplag.facade.Facade;
import com.autoplag.facade.TaskFacade;
import com.autoplag.facade.converter.Converter;
import com.autoplag.persistence.domain.Course;
import com.autoplag.persistence.domain.Task;
import com.autoplag.presentation.exception.BadRequestException;
import com.autoplag.presentation.payload.request.TaskRequestDto;
import com.autoplag.presentation.payload.response.PagedDto;
import com.autoplag.presentation.payload.response.TaskDto;
import com.autoplag.service.CourseService;
import com.autoplag.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Facade
@AllArgsConstructor
public class TaskFacadeImpl implements TaskFacade {
    private final CourseService courseService;
    private final TaskService taskService;

    private final Converter<Task, TaskDto> taskToTaskDto;
    private final Converter<TaskRequestDto, Task> taskRequestDtoToTask;

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

    @Override
    public void addTaskToCourse(Long userId, TaskRequestDto dto) {
        Course course = courseService.getCourseById(userId, dto.getCourseId())
                .orElseThrow(BadRequestException::new);
        Task newTask = taskRequestDtoToTask.convert(dto);
        newTask.setCourse(course);
        taskService.saveTask(newTask);
    }
}
