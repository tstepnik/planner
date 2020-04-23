package com.tstepnik.planner.repository;

import com.tstepnik.planner.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.user.login=?1")
    List<Task> findAllByLogin(String login);

    @Query("SELECT t FROM Task t WHERE t.id=?1 AND t.user.login=?2")
    Optional<Task> findUserTask(Long id,String login);

    @Query("DELETE FROM Task t WHERE t.id=?1 AND t.user.login=?2")
    void deleteUserTask(Long id, String login);
}
