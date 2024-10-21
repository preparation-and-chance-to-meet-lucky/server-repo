package com.lukcython.soundpin.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.lukcython.soundpin.domain.Playlists;
import com.lukcython.soundpin.dto.PlaylistRequest.InsertPlaylistRequest;
import com.lukcython.soundpin.dto.PlaylistResponse.PlaylistInfoResponse;
import com.lukcython.soundpin.repository.PlaylistRepository;
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
                .filter(playlist -> playlist.getSnippet().getTitle().substring(1,4).equalsIgnoreCase("pin"))
                .toList();

        return playlists.stream()
                .map(PlaylistInfoResponse::of)
                .map(playlist -> {
                    Optional<Playlists> play = playlistRepository.findByPlaylistId(playlist.getPlaylistId());
                    return playlist.of(play.orElseGet(() -> playlistRepository.save(Playlists.of(playlist))));
                }).toList();
    }

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
}
