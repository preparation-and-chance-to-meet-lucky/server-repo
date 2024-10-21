package com.lukcython.soundpin.dto;

import com.google.api.services.youtube.model.Playlist;
import com.lukcython.soundpin.domain.Playlists;
import lombok.Builder;
import lombok.Getter;

public class PlaylistResponse {

    @Getter
    public static class PlaylistInfoResponse {
        private Long id;
        private final String playlistId;
        private String customTitle;
        private final String title;
        private final String description;
        private final String imageUrl;
        private boolean canModify;

        @Builder
        public PlaylistInfoResponse(Long id, String playlistId, String customTitle, String title, String description, String imageUrl, boolean canModify) {
            this.id = id;
            this.playlistId = playlistId;
            this.customTitle = customTitle;
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
            this.canModify = canModify;
        }

        public static PlaylistInfoResponse of(Playlist response){
            return PlaylistInfoResponse.builder()
                    .playlistId(response.getId())
                    .title(response.getSnippet().getTitle())
                    .description(response.getSnippet().getDescription())
                    .imageUrl(response.getSnippet().getThumbnails().getDefault().getUrl())
                    .build();
        }

        public PlaylistInfoResponse of(Playlists playlists){
            this.id = playlists.getId();
            this.customTitle = playlists.getCustomTitle();
            this.canModify = playlists.isCanModify();
            return this;
        }
    }
}

