package dev.lsrdev.authbasejwt.commons.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.List;

public class HttpStatusException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3901000762616680152L;

    private final String message;
    private final HttpStatus httpStatus;
    private final List<String> messages;

    public HttpStatusException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.messages = null;
    }

    public HttpStatusException(List<String> messages) {
        this(messages, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public HttpStatusException(List<String> messages, HttpStatus httpStatus) {
        this.message = null;
        this.messages = messages;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public List<String> getMessages() {
        return messages;
    }

}
