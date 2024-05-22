package com.kusitms29.backendH.domain.sync.service;

import com.kusitms29.backendH.domain.sync.repository.FavoriteSyncRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteSyncManager {
    private final FavoriteSyncRepository favoriteSyncRepository;
    public Boolean existsByUserIdAndSyncId(Long userId, Long syncId){
        return favoriteSyncRepository.existsByUserIdAndSyncId(userId, syncId);
    }
}
