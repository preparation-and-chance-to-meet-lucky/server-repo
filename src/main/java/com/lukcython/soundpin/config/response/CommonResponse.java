package com.lukcython.soundpin.config.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse {
    boolean success;
    int code;
    String message;

    @Builder
    public CommonResponse(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
