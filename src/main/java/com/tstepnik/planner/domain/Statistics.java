package com.tstepnik.planner.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double productivity;

    private Integer archivedTasksCount;

    private Integer finishedTasksCount;

    private LocalDateTime creationDate;

    public Statistics(Long userId, Double productivity, Integer userArchivedTasks, Integer userFinishedTasks, LocalDateTime now) {
        this.userId = userId;
        this.productivity = productivity;
        this.archivedTasksCount = userArchivedTasks;
        this.finishedTasksCount = userFinishedTasks;
        this.creationDate = now;
    }
}
