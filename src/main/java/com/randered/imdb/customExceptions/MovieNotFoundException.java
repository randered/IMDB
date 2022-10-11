package com.randered.imdb.customExceptions;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException() {
        super("Movie not found.");
    }

}
