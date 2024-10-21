package com.lukcython.soundpin.dto;

import lombok.Getter;

@Getter
public class PlaylistRequest {

    @Getter
    public static class InsertPlaylistRequest {
        private String title;
        private String description;
        private String status; //"private" or "public"

    }

    @Getter
    public static class UpdatePlaylistRequest {
        private String title;
        private String description;
        private String status;
    }
}
