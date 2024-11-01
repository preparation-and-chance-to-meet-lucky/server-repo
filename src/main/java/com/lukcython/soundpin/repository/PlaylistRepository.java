package com.lukcython.soundpin.repository;

import com.lukcython.soundpin.domain.Playlists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlists, Long> {

    Optional<Playlists> findByPlaylistId(String playlistId);
    Optional<Playlists> findByPin(String pin);

    @Modifying
    @Query("DELETE from Playlists p WHERE p.playlistId=:playlistId")
    void deleteByPlaylistId(@Param("playlistId") String playlistId);
}
