package com.tstepnik.planner.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.ZonedDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String description;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    private Boolean isDone;

    private Long userId;

    private ZonedDateTime creationDate;

    @NotBeforeNow
    private ZonedDateTime plannedFor;



    public Task() {
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getPlannedFor() {
        return plannedFor;
    }

    public void setPlannedFor(ZonedDateTime plannedFor) {
        this.plannedFor = plannedFor;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }
}
