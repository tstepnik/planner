package com.tstepnik.planner.repository;

import com.tstepnik.planner.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getAllByUserId(Long userId);

    Optional<Task> findById(Long id);

//    Long getAllByUserIdAndDoneIsTrue(Long userId);

    @Query( "SELECT COUNT(t) FROM Task t WHERE t.userId=:userId AND t.plannedFor <= :now")
    Long countAllUserArchivedTasks(@Param("userId") Long userId, @Param("now") ZonedDateTime dateTime);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.done=true AND t.userId=?1 AND t.plannedFor <= ?2")
    Long countAllUserFinishAndArchivedTasks(Long userId,ZonedDateTime dateTime);
}
