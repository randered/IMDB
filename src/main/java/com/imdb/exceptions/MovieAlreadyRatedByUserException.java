package com.imdb.exceptions;

public class MovieAlreadyRatedByUserException extends RuntimeException {
    public MovieAlreadyRatedByUserException() {
        super("You have already rated this movie!");
    }
}
