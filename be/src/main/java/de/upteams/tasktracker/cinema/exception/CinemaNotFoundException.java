package de.upteams.tasktracker.cinema.exception;

import de.upteams.tasktracker.exception.handling.exceptions.common.RestApiException;
import org.springframework.http.HttpStatus;

public class CinemaNotFoundException extends RestApiException {

    public CinemaNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Cinema not found");
    }

    public CinemaNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, "Cinema not found with id: " + id);
    }
}
