package com.lukcython.soundpin.controller;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.lukcython.soundpin.config.response.ListResponse;
import com.lukcython.soundpin.config.response.SingleResponse;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/playlists/{userId}")
    public ResponseEntity<ListResponse<PlaylistInfoResponse>> getPlaylist(NetHttpTransport httpTransport, @PathVariable("userId") String userId) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(new ListResponse<>(200, "플레이리스트 반환 완료", playlistService.getPlaylist(httpTransport, userId)));
    }


    @PostMapping("/playlists")
    public ResponseEntity<SingleResponse<Void>> insertPlaylist(@RequestBody InsertPlaylistRequest playlistRequest) throws GeneralSecurityException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SingleResponse<>(201, "플레이리스트 추가 완료", playlistService.insertPlaylist(playlistRequest)));
    }

    @PutMapping("/playlists/youtube/{Id}")
    public ResponseEntity<SingleResponse<PlaylistInfoResponse>> updateYoutubePlaylist(@PathVariable("Id") Long Id, @RequestBody UpdatePlaylistRequest updatePlaylistRequest) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "(유튜브) 플레이리스트 수정 완료", playlistService.updateYoutubePlaylist(Id, updatePlaylistRequest)));
    }

    @PatchMapping("/playlists/modify/{Id}")
    public ResponseEntity<SingleResponse<Map<String, Boolean>>> updateModify(@PathVariable("Id") Long id){
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "플레이리스트 수정(modify) 여부 변경 완료", playlistService.updateModify(id)));
    }

    @PatchMapping("/playlists/title/{Id}")
    public ResponseEntity<SingleResponse<Map<String, String>>> updateTitle(@PathVariable("Id") Long id, @RequestBody Map<String, String> customTitle){
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "플레이리스트 제목 변경 완료", playlistService.updateTitle(id, customTitle)));
    }

    @DeleteMapping("/playlists/{Id}")
    public ResponseEntity<SingleResponse<Void>> deletePlaylist(@PathVariable("Id") Long Id) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "플레이리스트 삭제 완료", playlistService.deletePlaylist(Id)));
    }



    

}
