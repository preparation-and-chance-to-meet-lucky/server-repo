package com.lukcython.soundpin.dto;

import lombok.Getter;
import lombok.Setter;

public class PlaylistItemRequest {

    @Getter
    public static class InsertPlaylistItem {
        private String videoUrl;
        private Long position;
    }

    @Getter
    public static class UpdatePlaylistItem {
        private String playlistItemId;
        private String videoUrl;
        private Long position;
    }
}
