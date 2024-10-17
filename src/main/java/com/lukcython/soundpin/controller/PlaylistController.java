package com.lukcython.soundpin.controller;

import com.lukcython.soundpin.dto.CustomPlaylist.PlaylistInfoResposne;
import com.lukcython.soundpin.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/playlists")
    public ResponseEntity<List<PlaylistInfoResposne>> getPlaylist() throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(playlistService.getPlaylist());
    }

}
