package com.lukcython.soundpin.repository;

import com.lukcython.soundpin.domain.PlaylistItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItems, Long> {

    Optional<PlaylistItems> findByPlaylistItemId(String playlistItemId);

    @Modifying
    @Query("DELETE from PlaylistItems p WHERE p.playlistItemId=:playlistItemId")
    void deleteByPlaylistItemId(@Param("playlistItemId") String playlistItemId);
}
