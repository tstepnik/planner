package com.tstepnik.planner.domain.task;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min=3,max = 1000)
    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    private Boolean done = Boolean.FALSE;

    private Long userId;

    private LocalDateTime creationDate;

    @FutureOrPresent
    private LocalDateTime plannedFor;
}


