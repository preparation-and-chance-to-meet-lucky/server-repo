package com.lukcython.soundpin.dto;

import com.google.api.services.youtube.model.Playlist;
import lombok.Builder;
import lombok.Getter;

public class CustomPlaylist {

    @Getter
    public static class PlaylistInfoResposne{
        private String playlistId;
        private String title;
        private String description;
        private String imageUrl;

        @Builder
        public PlaylistInfoResposne(String playlistId, String title, String description, String imageUrl) {
            this.playlistId = playlistId;
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
        }

        public static PlaylistInfoResposne of(Playlist response){
            return PlaylistInfoResposne.builder()
                    .playlistId(response.getId())
                    .title(response.getSnippet().getTitle())
                    .description(response.getSnippet().getDescription())
                    .imageUrl(response.getSnippet().getThumbnails().getDefault().getUrl())
                    .build();
        }
    }
}

