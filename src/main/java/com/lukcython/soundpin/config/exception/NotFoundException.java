package com.lukcython.soundpin.config.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(ExceptionMessage e){
        super(e.getMessage());
    }
}
