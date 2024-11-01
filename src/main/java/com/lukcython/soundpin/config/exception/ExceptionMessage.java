package com.lukcython.soundpin.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessage {

    PLAYLIST_NOT_FOUND("Playlist를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PLAYLIST_ITEM_NOT_FOUND("PlaylistItem을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("User를 찾을 수 없습니다", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
