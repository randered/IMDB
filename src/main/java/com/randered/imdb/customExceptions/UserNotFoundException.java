package com.randered.imdb.customExceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User Not Found !");
    }

}
