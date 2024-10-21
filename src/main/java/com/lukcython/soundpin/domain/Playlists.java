package com.lukcython.soundpin.domain;

import com.lukcython.soundpin.dto.PlaylistResponse;
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
public class Playlists {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playlistId;

    private String customTitle;

    private boolean canModify;

    @Builder
    public Playlists(Long id, String playlistId, String customTitle, boolean canModify) {
        this.id = id;
        this.playlistId = playlistId;
        this.customTitle = customTitle;
        this.canModify = canModify;
    }

    public static Playlists of(PlaylistResponse.PlaylistInfoResponse playlist){
        return Playlists.builder()
                .playlistId(playlist.getPlaylistId())
                .customTitle("")
                .canModify(true)
                .build();
    }
}
