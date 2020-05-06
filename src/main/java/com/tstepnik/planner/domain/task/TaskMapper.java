package com.tstepnik.planner.domain.task;

import com.tstepnik.planner.domain.EntityMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO,Task> {
}
