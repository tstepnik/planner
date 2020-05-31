package com.tstepnik.planner.exceptions;

public class TaskExpiredException extends RuntimeException {
    public TaskExpiredException(String message) {
        super(message);
    }
}
