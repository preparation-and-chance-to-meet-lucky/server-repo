package com.lukcython.soundpin.repository;

import com.lukcython.soundpin.domain.Playlists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlists, Long> {

    Optional<Playlists> findByPlaylistId(String playlistId);
}
