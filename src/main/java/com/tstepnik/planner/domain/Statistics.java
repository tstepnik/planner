package com.tstepnik.planner.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

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

    private Integer numberOfTasks;

    private Integer numberOfFinishTasks;

    private ZonedDateTime creationDate;

    public Statistics(Long userId, Double productivity, Integer userArchivedTasks, Integer userFinishedTasks, ZonedDateTime now) {
        this.userId = userId;
        this.productivity = productivity;
        this.numberOfTasks = userArchivedTasks;
        this.numberOfFinishTasks = userFinishedTasks;
        this.creationDate = now;
    }
}
