package com.lukcython.soundpin.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.lukcython.soundpin.domain.Playlists;
import com.lukcython.soundpin.dto.PlaylistRequest.*;
import com.lukcython.soundpin.dto.PlaylistResponse.PlaylistInfoResponse;
import com.lukcython.soundpin.repository.PlaylistRepository;
import com.lukcython.soundpin.util.youtube.YoutubeApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public List<PlaylistInfoResponse> getPlaylist() throws GeneralSecurityException, IOException {
        YouTube youtubeService = YoutubeApiUtil.getService();
        YouTube.Playlists.List request = youtubeService.playlists()
                .list(Collections.singletonList("snippet"));
        PlaylistListResponse response = request.setMaxResults(25L)
                .setMine(true)
                .execute();

        //Youtube playlist
        List<Playlist> playlists = response.getItems().stream()
                .filter(playlist -> playlist.getSnippet().getTitle().length() > 4)
                .filter(playlist -> playlist.getSnippet().getTitle().substring(1,4).equalsIgnoreCase("pin"))
                .toList();

        return playlists.stream()
                .map(PlaylistInfoResponse::of)
                .map(playlist -> {
                    Optional<Playlists> play = playlistRepository.findByPlaylistId(playlist.getPlaylistId());
                    return playlist.of(play.orElseGet(() -> playlistRepository.save(Playlists.of(playlist))));
                }).toList();
    }

    @Transactional
    public Void insertPlaylist(InsertPlaylistRequest insertPlaylistRequest) throws GeneralSecurityException, IOException {
        YouTube youtubeService = YoutubeApiUtil.getService();
        Playlist playlist = new Playlist();

        // Add the snippet object property to the Playlist object.
        PlaylistSnippet snippet = new PlaylistSnippet();
        snippet.setDefaultLanguage("en");
        snippet.setDescription(insertPlaylistRequest.getDescription());
        snippet.setTitle(insertPlaylistRequest.getTitle());
        playlist.setSnippet(snippet);

        // Add the status object property to the Playlist object.
        PlaylistStatus status = new PlaylistStatus();
        status.setPrivacyStatus(insertPlaylistRequest.getStatus());
        playlist.setStatus(status);

        // Define and execute the API request
        youtubeService.playlists()
                .insert(Collections.singletonList("snippet,status"), playlist).execute();
        return null;
    }

    @Transactional
    public PlaylistInfoResponse updateYoutubePlaylist(String playlistId, UpdatePlaylistRequest updatePlaylistRequest) throws GeneralSecurityException, IOException {
        YouTube youtubeService = YoutubeApiUtil.getService();
        Playlist playlist = new Playlist();

        // Add the id string property to the Playlist object.
        playlist.setId(playlistId);

        // Add the snippet object property to the Playlist object.
        PlaylistSnippet snippet = new PlaylistSnippet();
        snippet.setDescription(updatePlaylistRequest.getDescription());
        snippet.setTitle(updatePlaylistRequest.getTitle());
        playlist.setSnippet(snippet);

        // Add the status object property to the Playlist object.
        PlaylistStatus status = new PlaylistStatus();
        status.setPrivacyStatus(updatePlaylistRequest.getStatus());
        playlist.setStatus(status);

        // Define and execute the API request
        youtubeService.playlists()
                .update(Collections.singletonList("snippet,status"), playlist);
        return PlaylistInfoResponse.of(playlist).of(playlistRepository.findByPlaylistId(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist를 찾을 수 없습니다.")));
    }

    @Transactional
    public Map<String, Boolean> updateModify(Long id) {
        Playlists playlists =playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist를 찾을 수 없습니다."));
        playlists.isModify();
        Map<String, Boolean> map = new HashMap<>();
        map.put("canModify", playlists.isCanModify());
        return map;
    }

    @Transactional
    public  Map<String, String> updateTitle(Long id,  Map<String, String> customTitle) {
        Playlists playlists = playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist를 찾을 수 없습니다."));
        playlists.isTitle(customTitle.get("customTitle"));
        Map<String, String> map = new HashMap<>();
        map.put("customTitle", playlists.getCustomTitle());
        return map;
    }

    @Transactional
    public Void deletePlaylist(String playlistId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = YoutubeApiUtil.getService();
        // Define and execute the API request
        YouTube.Playlists.Delete request = youtubeService.playlists()
                .delete(playlistId);
        request.execute();

        playlistRepository.deleteByPlaylistId(playlistId);
        return null;
    }
}
