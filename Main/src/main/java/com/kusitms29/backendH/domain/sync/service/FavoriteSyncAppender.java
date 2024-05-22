package com.kusitms29.backendH.domain.sync.service;

import com.kusitms29.backendH.domain.sync.entity.FavoriteSync;
import com.kusitms29.backendH.domain.sync.repository.FavoriteSyncRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteSyncAppender {
    private final FavoriteSyncRepository favoriteSyncRepository;
    @Transactional
    public void saveFavoriteSync(FavoriteSync favoriteSync){
        favoriteSyncRepository.save(favoriteSync);
    }
}
