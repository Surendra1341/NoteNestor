package com.notes.notenestor.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {

    private Map<String, Object> error;

    public ValidationException(Map<String, Object> error) {
        super("Validation failed");
        this.error = error;
    }


}
