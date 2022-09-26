package com.randered.imdb.customExceptions;

import static com.randered.imdb.util.common.Constants.USER_ALREADY_EXISTS;

public class UserAlreadyExistAuthenticationException extends RuntimeException {
    public UserAlreadyExistAuthenticationException(final String username) {
        super(String.format(USER_ALREADY_EXISTS, username));
    }
}
