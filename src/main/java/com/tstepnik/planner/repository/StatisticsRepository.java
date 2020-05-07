package com.tstepnik.planner.repository;

import com.tstepnik.planner.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics,Long> {

    Statistics findFirstByOrderByIdDesc();
}
