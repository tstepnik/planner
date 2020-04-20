package com.tstepnik.planner.domain.Task;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDTO taskToTaskDTO(Task task);
    List<TaskDTO> taskToTaskDTO(List<Task> tasks);
}
