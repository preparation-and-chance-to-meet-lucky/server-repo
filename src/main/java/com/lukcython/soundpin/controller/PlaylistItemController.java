package com.lukcython.soundpin.controller;

import com.lukcython.soundpin.dto.PlaylistItemRequest.*;
import com.lukcython.soundpin.dto.PlaylistItemResponse.*;
import com.lukcython.soundpin.service.PlaylistItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/playlistItems")
    public ResponseEntity<Void> insertPlaylistItem(@RequestBody InsertPlaylistItem insertPlaylistItem) throws GeneralSecurityException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playlistItemService.insertPlaylistItem(insertPlaylistItem));
    }

    @PutMapping("/playlistItems/youtube")
    public ResponseEntity<PlaylistItemInfoResponse> updateYoutubePlaylistItem(@RequestBody UpdatePlaylistItem updatePlaylistItem) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(playlistItemService.updateYoutubePlaylistItem(updatePlaylistItem));
    }

    @PatchMapping("/playlistItems/likes/{Id}")
    public ResponseEntity<Map<String, Long>> updateLikes(@PathVariable("Id") Long id){
        return ResponseEntity.ok()
                .body(playlistItemService.updateLikes(id));
    }

    @DeleteMapping("/playlistItems/{playlistItemId}")
    public ResponseEntity<Void> deletePlaylistItem(@PathVariable("playlistItemId") String playlistItemId) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(playlistItemService.deletePlaylistItem(playlistItemId));
    }

}
