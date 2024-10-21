package com.lukcython.soundpin.dto;

import com.google.api.services.youtube.model.PlaylistItem;
import com.lukcython.soundpin.domain.PlaylistItems;
import lombok.Builder;
import lombok.Getter;

public class PlaylistItemResponse {

    @Getter
    public static class PlaylistItemInfoResponse {

        private Long id;

        private final String playlistItemId;

        private final String videoId;

        private final String videoTitle;

        private final String imageUrl;

        private final int position;

        private final String videoOwnerChannelTitle;

        private Long like;

        @Builder
        public PlaylistItemInfoResponse(Long id, String playlistItemId, String videoId, String videoTitle, String imageUrl, int position, String videoOwnerChannelTitle) {
            this.id = id;
            this.playlistItemId = playlistItemId;
            this.videoId = videoId;
            this.videoTitle = videoTitle;
            this.imageUrl = imageUrl;
            this.position = position;
            this.videoOwnerChannelTitle = videoOwnerChannelTitle;
        }

        public static PlaylistItemInfoResponse of (PlaylistItem playlistItem){
            return PlaylistItemInfoResponse.builder()
                    .videoId(playlistItem.getSnippet().getResourceId().getVideoId())
                    .playlistItemId(playlistItem.getId())
                    .imageUrl(playlistItem.getSnippet().getThumbnails() == null? null : playlistItem.getSnippet().getThumbnails().getDefault().getUrl())
                    .position(playlistItem.getSnippet().getPosition().intValue())
                    .videoTitle(playlistItem.getSnippet().getTitle())
                    .videoOwnerChannelTitle(playlistItem.getSnippet().getVideoOwnerChannelTitle())
                    .build();
        }

        public PlaylistItemInfoResponse of(PlaylistItems playlistItems) {
            this.id = playlistItems.getId();
            this.like = playlistItems.getLikes();
            return this;
        }
    }
}
