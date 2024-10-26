package com.lukcython.soundpin.controller;

import com.lukcython.soundpin.config.response.ListResponse;
import com.lukcython.soundpin.config.response.SingleResponse;
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
    public ResponseEntity<ListResponse<PlaylistItemInfoResponse>> getPlaylistItems(@PathVariable("playlistId") String playlistId) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(new ListResponse<>(200, "플레이리스트 아이템 목록 반환 성공", playlistItemService.getPlaylistItems(playlistId)));
    }

    @PostMapping("/playlistItems")
    public ResponseEntity<SingleResponse<Void>> insertPlaylistItem(@RequestBody InsertPlaylistItem insertPlaylistItem) throws GeneralSecurityException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SingleResponse<>(201, "플레이리스트 아이템 추가 성공", playlistItemService.insertPlaylistItem(insertPlaylistItem)));
    }

    @PutMapping("/playlistItems/youtube")
    public ResponseEntity<SingleResponse<PlaylistItemInfoResponse>> updateYoutubePlaylistItem(@RequestBody UpdatePlaylistItem updatePlaylistItem) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "(유튜브) 플레이리스트 아이템 순서 수정 완료", playlistItemService.updateYoutubePlaylistItem(updatePlaylistItem)));
    }

    @PatchMapping("/playlistItems/likes/{Id}")
    public ResponseEntity<SingleResponse<Map<String, Long>>> updateLikes(@PathVariable("Id") Long id){
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "플레이리스트 아이템 좋아요 반영 완료", playlistItemService.updateLikes(id)));
    }

    @DeleteMapping("/playlistItems/{playlistItemId}")
    public ResponseEntity<SingleResponse<Void>> deletePlaylistItem(@PathVariable("playlistItemId") String playlistItemId) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "플레이리스트 아이템 삭제 완료", playlistItemService.deletePlaylistItem(playlistItemId)));
    }

}
