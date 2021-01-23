package com.magenta.myexception;

public class AuthorizationException extends Exception{

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
