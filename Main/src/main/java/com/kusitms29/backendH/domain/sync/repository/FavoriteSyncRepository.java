package com.kusitms29.backendH.domain.sync.repository;

import com.kusitms29.backendH.domain.sync.entity.FavoriteSync;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteSyncRepository extends JpaRepository<FavoriteSync, Long> {
    List<FavoriteSync> findAllByUserId(Long userId);
}
