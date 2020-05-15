package com.tstepnik.planner.repository;

import com.tstepnik.planner.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics,Long> {

    Statistics findFirstByUserIdOrderByIdDesc(Long userId);
}
