package com.lukcython.soundpin.config.exception;

public class UserException extends RuntimeException{
    public UserException(ExceptionMessage e){
        super(e.getMessage());
    }
}
