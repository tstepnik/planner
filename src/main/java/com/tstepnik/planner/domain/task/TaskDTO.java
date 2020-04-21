package com.tstepnik.planner.domain.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO {

    private String description;
    private boolean isDone;
    private boolean isArchived;

    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    private Importance importance;

}