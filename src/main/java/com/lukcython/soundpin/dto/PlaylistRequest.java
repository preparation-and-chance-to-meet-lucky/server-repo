package com.lukcython.soundpin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PlaylistRequest {

    @Getter
    @Setter
    public static class InsertPlaylistRequest {
        private String title;
        private String description;
        private String status; //"private" or "public"

    }
}
