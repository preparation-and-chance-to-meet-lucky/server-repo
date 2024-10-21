package com.lukcython.soundpin.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.lukcython.soundpin.domain.PlaylistItems;
import com.lukcython.soundpin.domain.Playlists;
import com.lukcython.soundpin.dto.PlaylistItemResponse;
import com.lukcython.soundpin.dto.PlaylistItemResponse.*;
import com.lukcython.soundpin.dto.PlaylistResponse;
import com.lukcython.soundpin.repository.PlaylistItemRepository;
import com.lukcython.soundpin.util.youtube.YoutubeApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistItemService {

    private final PlaylistItemRepository playlistItemRepository;


    public List<PlaylistItemInfoResponse> getPlaylistItems(String playlistId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = YoutubeApiUtil.getService();
        YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                .list(Collections.singletonList("snippet"));
        PlaylistItemListResponse response = request.setMaxResults(25L)
                .setPlaylistId(playlistId)
                .execute();
        List<PlaylistItem> playlistItems = response.getItems();

        return playlistItems.stream()
                .map(PlaylistItemInfoResponse::of)
                .map(playlistItem -> {
                    Optional<PlaylistItems> play = playlistItemRepository.findByVideoId(playlistItem.getVideoId());
                    return playlistItem.of(play.orElseGet(() -> playlistItemRepository.save(PlaylistItems.of(playlistItem))));
                }).toList();
    }
}
