package com.kusitms29.backendH.domain.sync.service;

import com.kusitms29.backendH.domain.sync.entity.FavoriteSync;
import com.kusitms29.backendH.domain.sync.repository.FavoriteSyncRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteSyncReader {
    private final FavoriteSyncRepository favoriteSyncRepository;

    public List<FavoriteSync> findAllByUserId(Long userId){
        return favoriteSyncRepository.findAllByUserId(userId);
    }
}
