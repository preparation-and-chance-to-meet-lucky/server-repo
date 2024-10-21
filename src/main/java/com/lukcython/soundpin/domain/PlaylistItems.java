package com.lukcython.soundpin.domain;

import com.lukcython.soundpin.dto.PlaylistItemResponse.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistItems {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoId;

    private Long likes;

    @Builder
    public PlaylistItems(Long id, String videoId, Long likes) {
        this.id = id;
        this.videoId = videoId;
        this.likes = likes;
    }

    public static PlaylistItems of(PlaylistItemInfoResponse playlistItem) {
        return PlaylistItems.builder()
                .videoId(playlistItem.getVideoId())
                .likes(0L)
                .build();
    }
}
