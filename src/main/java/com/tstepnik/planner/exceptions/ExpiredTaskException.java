package com.tstepnik.planner.exceptions;

public class ExpiredTaskException extends RuntimeException {
    public ExpiredTaskException(String message) {
        super(message);
    }
}
