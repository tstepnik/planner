package com.tstepnik.planner.repository;

import com.tstepnik.planner.domain.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> getAllByUserId(Long userId, Pageable pageable);

    Optional<Task> findById(Long id);

}
