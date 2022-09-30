package com.randered.imdb.customExceptions;

public class MovieAlreadyRatedByUserException extends RuntimeException {
    public MovieAlreadyRatedByUserException() {
        super("You have already rated this movie!");
    }
}
