package com.tstepnik.planner.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.net.Inet4Address;

@Entity
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double productivity;

    private Integer numberOfTasks;

    private Integer numberOfFinishTasks;

    public Statistics() {
    }
    public Statistics(Long userId, Double productivity, Integer numberOfTasks,Integer numberOfFinishTasks){
        this.userId=userId;
        this.productivity=productivity;
        this.numberOfTasks=numberOfTasks;
        this.numberOfFinishTasks=numberOfFinishTasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getProductivity() {
        return productivity;
    }

    public void setProductivity(Double productivity) {
        this.productivity = productivity;
    }

    public Integer getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(Integer numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public Integer getNumberOfFinishTasks() {
        return numberOfFinishTasks;
    }

    public void setNumberOfFinishTasks(Integer numberOfFinishTasks) {
        this.numberOfFinishTasks = numberOfFinishTasks;
    }
}
