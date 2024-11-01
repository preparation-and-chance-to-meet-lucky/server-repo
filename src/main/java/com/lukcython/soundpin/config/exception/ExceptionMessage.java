package com.lukcython.soundpin.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessage {

    YOUTUBE_PLAYLIST_NOT_FOUND("해당하는 유튜브 재생목록를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

    PLAYLIST_NOT_FOUND("Playlist를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PLAYLIST_ITEM_NOT_FOUND("PlaylistItem을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
