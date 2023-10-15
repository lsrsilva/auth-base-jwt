package dev.lsrdev.authbasejwt.infra.exceptions;

public class InvalidJwtTokenException extends RuntimeException {

    public InvalidJwtTokenException(String e) {
        super(e);
    }

}
