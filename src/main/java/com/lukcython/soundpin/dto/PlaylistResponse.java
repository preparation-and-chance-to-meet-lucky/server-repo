package com.lukcython.soundpin.dto;

import com.google.api.services.youtube.model.Playlist;
import com.lukcython.soundpin.domain.Playlists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PlaylistResponse {

    protected Long id;
    protected String playlistId;
    protected String customTitle;
    protected String title;
    protected String description;
    protected String imageUrl;
    protected String status;

    @Builder
    public PlaylistResponse(Long id, String playlistId, String customTitle, String title, String description, String imageUrl, String status) {
        this.id = id;
        this.playlistId = playlistId;
        this.customTitle = customTitle;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public static PlaylistResponse of(Playlist playlist) {
        return PlaylistResponse.builder()
                .playlistId(playlist.getId())
                .title(playlist.getSnippet().getTitle())
                .description(playlist.getSnippet().getDescription())
                .imageUrl(playlist.getSnippet().getThumbnails() == null ? null : playlist.getSnippet().getThumbnails().getDefault().getUrl())
                .status(playlist.getStatus().getPrivacyStatus())
                .build();
    }

    public PlaylistResponse of(Playlists playlists) {
        this.id = playlists.getId();
        this.customTitle = playlists.getCustomTitle();
        return this;
    }

    @Getter
    @Setter
    public static class PlaylistInfoResponse extends PlaylistResponse {
        private boolean canModify;

        public PlaylistInfoResponse(Long id, String playlistId, String customTitle, String title, String description, String imageUrl, boolean canModify, String status) {
            super(id, playlistId, customTitle, title, description, imageUrl, status);
            this.canModify = canModify;
        }

        public static PlaylistInfoResponse of(PlaylistResponse playlist, Playlists playlists) {
            return new PlaylistInfoResponse(
                    playlists.getId(),
                    playlist.getPlaylistId(),
                    playlists.getCustomTitle(),
                    playlist.getTitle(),
                    playlist.getDescription(),
                    playlist.getImageUrl(),
                    playlists.isCanModify(),
                    playlist.getStatus()
            );
        }
    }
}
