package com.lukcython.soundpin.controller;

import com.lukcython.soundpin.dto.PlaylistRequest.InsertPlaylistRequest;
import com.lukcython.soundpin.dto.PlaylistResponse.PlaylistInfoResponse;
import com.lukcython.soundpin.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/playlists")
    public ResponseEntity<List<PlaylistInfoResponse>> getPlaylist() throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(playlistService.getPlaylist());
    }


    @PostMapping("/playlists")
    public ResponseEntity<Void> insertPlaylist(@RequestBody InsertPlaylistRequest playlistRequest) throws GeneralSecurityException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playlistService.insertPlaylist(playlistRequest));
    }

    

}
