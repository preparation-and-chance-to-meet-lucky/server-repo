package com.lukcython.soundpin.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.lukcython.soundpin.dto.CustomPlaylist.PlaylistInfoResposne;
import com.lukcython.soundpin.util.youtube.MyPlaylist;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class PlaylistService {

    public List<PlaylistInfoResposne> getPlaylist() throws GeneralSecurityException, IOException {
        YouTube youtubeService = MyPlaylist.getService();
        YouTube.Playlists.List request = youtubeService.playlists()
                .list(Collections.singletonList("snippet"));
        PlaylistListResponse response = request.setMaxResults(25L)
                .setMine(true)
                .execute();
        return response.getItems().stream()
                .filter(playlist -> playlist.getSnippet().getTitle().substring(1,4).equalsIgnoreCase("pin"))
                .map(PlaylistInfoResposne::of).toList();
    }
}
