package de.upteams.tasktracker.exception.handling.exceptions.common;

import org.springframework.http.HttpStatus;

public class FieldValidationException extends RestApiException {

    private final String field;

    public FieldValidationException(String field, String message) {
        super(HttpStatus.BAD_REQUEST, message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
