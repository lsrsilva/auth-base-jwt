package dev.lsrdev.authbasejwt.commons.exceptions.handlers;

import dev.lsrdev.authbasejwt.commons.exceptions.HttpStatusException;
import dev.lsrdev.authbasejwt.rest.JsonResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HttpStatusExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(HttpStatusExceptionHandler.class);

    @ExceptionHandler(value = {HttpStatusException.class})
    protected ResponseEntity<JsonResponseDTO> handleConflict(HttpStatusException ex) {
        return new ResponseEntity<>(JsonResponseDTO.otherReponse(
                ex.getMessage(),
                ex.getMessages()
        ), ex.getHttpStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<JsonResponseDTO> handleConflict(Exception ex) {
        log.error("Exception Handler: {}.", ex.getMessage(), ex);
        return new ResponseEntity<>(
                JsonResponseDTO.otherReponse(
                        "Internal Server Error!"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
