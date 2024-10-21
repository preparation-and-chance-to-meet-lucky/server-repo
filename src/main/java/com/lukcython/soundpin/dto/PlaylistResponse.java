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
        private String status;

        @Builder
        public PlaylistInfoResponse(Long id, String playlistId, String customTitle, String title, String description, String imageUrl, boolean canModify, String status) {
            this.id = id;
            this.playlistId = playlistId;
            this.customTitle = customTitle;
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
            this.canModify = canModify;
            this.status = status;
        }

        public static PlaylistInfoResponse of(Playlist playlist){
            return PlaylistInfoResponse.builder()
                    .playlistId(playlist.getId())
                    .title(playlist.getSnippet().getTitle())
                    .description(playlist.getSnippet().getDescription())
                    .imageUrl(playlist.getSnippet().getThumbnails() == null ? null : playlist.getSnippet().getThumbnails().getDefault().getUrl())
                    .status(playlist.getStatus().getPrivacyStatus())
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

