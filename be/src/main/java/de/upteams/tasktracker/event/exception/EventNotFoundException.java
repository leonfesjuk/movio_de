package de.upteams.tasktracker.event.exception;

import de.upteams.tasktracker.exception.handling.exceptions.common.RestApiException;
import org.springframework.http.HttpStatus;

public class EventNotFoundException extends RestApiException {

    public EventNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Event not found");
    }

    public EventNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, "Event not found with id: " + id);
    }
}
