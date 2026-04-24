package de.upteams.tasktracker.invitetoken.exception;

import de.upteams.tasktracker.exception.handling.exceptions.common.RestApiException;
import org.springframework.http.HttpStatus;

public class InvalidInviteTokenException extends RestApiException {

    public InvalidInviteTokenException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
