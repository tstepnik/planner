package com.tstepnik.planner.repository;

import com.tstepnik.planner.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getAllByUserId(Long userId);

    Optional<Task> findById(Long id);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.userId = ?1 AND t.plannedFor <= ?2")
    Long countAllUserArchivedTasks(Long userId, ZonedDateTime dateTime);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.done=true AND t.userId = ?1 AND t.plannedFor <= ?2")
    Long countAllUserFinishAndArchivedTasks(Long userId, ZonedDateTime dateTime);
}
