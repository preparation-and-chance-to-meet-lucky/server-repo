package com.lukcython.soundpin.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import com.lukcython.soundpin.config.exception.ExceptionMessage;
import com.lukcython.soundpin.config.exception.NotFoundException;
import com.lukcython.soundpin.domain.PlaylistItems;
import com.lukcython.soundpin.domain.Playlists;
import com.lukcython.soundpin.dto.PlaylistItemRequest.InsertPlaylistItem;
import com.lukcython.soundpin.dto.PlaylistItemRequest.UpdatePlaylistItem;
import com.lukcython.soundpin.dto.PlaylistItemResponse.PlaylistItemInfoResponse;
import com.lukcython.soundpin.repository.PlaylistItemRepository;
import com.lukcython.soundpin.repository.PlaylistRepository;
import com.lukcython.soundpin.util.youtube.YoutubeApiUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaylistItemService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistItemRepository playlistItemRepository;
    private final HttpSession httpSession;

    private static String USER;


    public List<PlaylistItemInfoResponse> getPlaylistItems(Long Id) throws GeneralSecurityException, IOException {
        USER = (String) httpSession.getAttribute("userId");
        Playlists playlist = playlistRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PLAYLIST_ITEM_NOT_FOUND));
        YouTube youtubeService = YoutubeApiUtil.getService(USER);
        YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                .list(Collections.singletonList("snippet"));
        PlaylistItemListResponse response = request.setMaxResults(25L)
                .setPlaylistId(playlist.getPlaylistId())
                .execute();
        List<PlaylistItem> playlistItems = response.getItems();

        return playlistItems.stream()
                .map(PlaylistItemInfoResponse::of)
                .map(Item -> {
                    Optional<PlaylistItems> play = playlistItemRepository.findByPlaylistItemId(Item.getPlaylistItemId());
                    return Item.of(play.orElseGet(() -> playlistItemRepository.save(PlaylistItems.of(Item))));
                }).toList();
    }

    @Transactional
    public Void insertPlaylistItem(InsertPlaylistItem insertPlaylistItem) throws GeneralSecurityException, IOException {
        YouTube youtubeService = YoutubeApiUtil.getService(USER);

        // Define the PlaylistItem object, which will be uploaded as the request body.
        PlaylistItem playlistItem = new PlaylistItem();

        // Add the snippet object property to the PlaylistItem object.
        PlaylistItemSnippet snippet = new PlaylistItemSnippet();
        snippet.setPlaylistId(insertPlaylistItem.getPlaylistId());
        snippet.setPosition(insertPlaylistItem.getPosition());
        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        String videoId = insertPlaylistItem.getVideoUrl().split("v=")[1];
        resourceId.setVideoId(videoId);
        snippet.setResourceId(resourceId);
        playlistItem.setSnippet(snippet);

        // Define and execute the API request
        youtubeService.playlistItems()
                .insert(Collections.singletonList("snippet"), playlistItem).execute();
        return null;
    }

    @Transactional
    public PlaylistItemInfoResponse updateYoutubePlaylistItem(UpdatePlaylistItem updatePlaylistItem) throws GeneralSecurityException, IOException {
        YouTube youtubeService = YoutubeApiUtil.getService(USER);

        // Define the PlaylistItem object, which will be uploaded as the request body.
        PlaylistItem playlistItem = new PlaylistItem();

        // Add the id string property to the PlaylistItem object.
        playlistItem.setId(updatePlaylistItem.getPlaylistItemId());

        // Add the snippet object property to the PlaylistItem object.
        PlaylistItemSnippet snippet = new PlaylistItemSnippet();
        snippet.setPlaylistId(updatePlaylistItem.getPlaylistId());
        snippet.setPosition(updatePlaylistItem.getPosition());
        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        String videoId = updatePlaylistItem.getVideoUrl().split("v=")[1];
        resourceId.setVideoId(videoId);
        snippet.setResourceId(resourceId);
        playlistItem.setSnippet(snippet);

        // Define and execute the API request
        PlaylistItem response = youtubeService.playlistItems()
                .update(Collections.singletonList("snippet"), playlistItem).execute();
        return PlaylistItemInfoResponse.of(response).of(playlistItemRepository.findByPlaylistItemId(updatePlaylistItem.getPlaylistItemId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PLAYLIST_ITEM_NOT_FOUND)));
    }

    @Transactional
    //유저 추가
    public Map<String, Long> updateLikes(Long id) {
        PlaylistItems playlistItems = playlistItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PLAYLIST_ITEM_NOT_FOUND));
        playlistItems.isLikes();
        Map<String, Long> map = new HashMap<>();
        map.put("Likes", playlistItems.getLikes());
        return map;
    }

    @Transactional
    public Void deletePlaylistItem(Long Id) throws GeneralSecurityException, IOException {
        PlaylistItems playlistItems = playlistItemRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PLAYLIST_ITEM_NOT_FOUND));
        YouTube youtubeService = YoutubeApiUtil.getService(USER);
        // Define and execute the API request
        youtubeService.playlistItems()
                .delete(playlistItems.getPlaylistItemId()).execute();
        playlistItemRepository.deleteById(Id);
        return null;
    }
}
