package com.tstepnik.planner.repository;

import com.tstepnik.planner.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getAllByUserId(Long userId);

    Optional<Task> findById(Long id);

}
