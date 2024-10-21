package com.lukcython.soundpin.controller;

import com.lukcython.soundpin.dto.PlaylistRequest.InsertPlaylistRequest;
import com.lukcython.soundpin.dto.PlaylistRequest.UpdatePlaylistRequest;
import com.lukcython.soundpin.dto.PlaylistResponse.PlaylistInfoResponse;
import com.lukcython.soundpin.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

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

    @PutMapping("/playlists/youtube/{playlistsId}")
    public ResponseEntity<PlaylistInfoResponse> updateYoutubePlaylist(@PathVariable("playlistsId") String playlistId, @RequestBody UpdatePlaylistRequest updatePlaylistRequest) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(playlistService.updateYoutubePlaylist(playlistId, updatePlaylistRequest));
    }

    @PatchMapping("/playlists/modify/{Id}")
    public ResponseEntity<Map<String, Boolean>> updateModify(@PathVariable("Id") Long id){
        return ResponseEntity.ok()
                .body(playlistService.updateModify(id));
    }

    @PatchMapping("/playlists/title/{Id}")
    public ResponseEntity<Map<String, String>> updateTitle(@PathVariable("Id") Long id, @RequestBody Map<String, String> customTitle){
        return ResponseEntity.ok()
                .body(playlistService.updateTitle(id, customTitle));
    }

    @DeleteMapping("/playlists/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable("playlistId") String playlistId) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(playlistService.deletePlaylist(playlistId));
    }



    

}
