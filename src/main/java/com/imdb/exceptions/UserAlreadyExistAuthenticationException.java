package com.imdb.exceptions;

import com.imdb.util.common.Constants;

public class UserAlreadyExistAuthenticationException extends RuntimeException {
    public UserAlreadyExistAuthenticationException(final String username) {
        super(String.format(Constants.USER_ALREADY_EXISTS, username));
    }
}
