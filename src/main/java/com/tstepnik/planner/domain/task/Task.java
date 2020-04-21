package com.tstepnik.planner.domain.task;
import com.tstepnik.planner.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String description;

    private boolean isDone;

    private boolean isArchived;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @ManyToOne
    private User user;

    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

    public Task(String description, boolean isDone, boolean isArchived, Importance importance) {
        this.description = description;
        this.isDone = isDone;
        this.isArchived = isArchived;
        this.importance = importance;
    }
}