package com.lukcython.soundpin.repository;

import com.lukcython.soundpin.domain.PlaylistItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItems, Long> {

    Optional<PlaylistItems> findByVideoId(String videoId);
}
