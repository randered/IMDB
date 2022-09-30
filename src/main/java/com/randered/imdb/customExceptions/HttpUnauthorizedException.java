package com.randered.imdb.customExceptions;

public class HttpUnauthorizedException extends RuntimeException {
    public HttpUnauthorizedException(final Throwable cause) {
        super(cause);
    }
}
