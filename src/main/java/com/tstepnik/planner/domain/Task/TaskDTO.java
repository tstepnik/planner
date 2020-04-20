package com.tstepnik.planner.domain.Task;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class TaskDTO {

    private String description;
    private boolean isDone;
    private boolean isArchived;

    @Enumerated(EnumType.STRING)
    private Importance importance;
}
