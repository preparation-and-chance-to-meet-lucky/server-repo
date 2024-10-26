package com.lukcython.soundpin.config.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponse extends CommonResponse {

    @Builder
    public ExceptionResponse(String message, int code) {
        super(false, code, message);
    }
}
