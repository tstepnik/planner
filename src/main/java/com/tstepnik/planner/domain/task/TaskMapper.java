package com.tstepnik.planner.domain.task;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toTask(TaskDto taskDto);

    List<TaskDto> toDto(List<Task> tasks);

    List<Task> toTasks(List<TaskDto> tasksDto);
}
