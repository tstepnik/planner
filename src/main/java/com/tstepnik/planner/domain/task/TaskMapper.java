package com.tstepnik.planner.domain.task;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDTO toDto(Task task);

    Task toTask(TaskDTO taskDto);

    List<TaskDTO> toDto(List<Task> tasks);

    List<Task> toTasks(List<TaskDTO> tasksDto);
}
