package com.ansv.taskmanagement.handler.authentication;

import javax.naming.AuthenticationException;

public class JwtTokenNotValidException extends AuthenticationException {

    public JwtTokenNotValidException(String msg) {
        super(msg);
    }
}
