package com.magenta.myexception;

public class DatabaseException extends Exception{


    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }
}
