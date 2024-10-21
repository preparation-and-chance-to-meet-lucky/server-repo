package com.lukcython.soundpin.controller;

import com.lukcython.soundpin.dto.PlaylistItemResponse;
import com.lukcython.soundpin.dto.PlaylistItemResponse.*;
import com.lukcython.soundpin.service.PlaylistItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaylistItemController {

    private final PlaylistItemService playlistItemService;

    @GetMapping("/playlistItems/{playlistId}")
    public ResponseEntity<List<PlaylistItemInfoResponse>> getPlaylistItems(@PathVariable("playlistId") String playlistId) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(playlistItemService.getPlaylistItems(playlistId));
    }
}
