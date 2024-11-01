package com.lukcython.soundpin.config.exception;

public class YoutubeException extends RuntimeException{
    public YoutubeException (ExceptionMessage e){
        super(e.getMessage());
    }
}
