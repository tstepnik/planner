package com.tstepnik.planner.controller;

import com.tstepnik.planner.domain.Statistics;
import com.tstepnik.planner.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    public final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mine")
    public ResponseEntity<Statistics> getStatistics(){
       return ResponseEntity.ok(statisticsService.createSaveAndReturnStatistics());
    }
}
