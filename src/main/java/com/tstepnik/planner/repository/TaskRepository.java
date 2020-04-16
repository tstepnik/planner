package com.tstepnik.planner.repository;

import com.tstepnik.planner.domain.Task.Importance;
import com.tstepnik.planner.domain.Task.Task;
import com.tstepnik.planner.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByUser(User user);

    @Query("SELECT t FROM Task t WHERE t.user.id=?1")
    List<Task> findALLByUserId(Long id);

    List<Task> findAllByImportance(Importance importance);

    @Query("SELECT t FROM Task t WHERE t.user.id=?1 AND t.importance=?2")
    List<Task> findAllByUserIdaAndImportance(Long id,Importance importance);
}
