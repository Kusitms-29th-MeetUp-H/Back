package com.kusitms29.backendH.domain.sync.application.service;

import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.domain.service.SyncReader;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.domain.UserCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SyncManageService {
    private final SyncReader syncReader;
    private final UserReader userReader;
    private final UserCategory userCategory;
    private final SyncCategoryReader syncCategoryReader;
    private final ParticiationManager particiationManager;
    public List<SyncInfoResponseDto> recommendSync(Long userId){
        User user = userReader.findByUserId(userId);
        List<UserCategory> userCategories = findAllByUserId(userId);
        List<String> categories = getTypeByCategories(userCategories);
        List<Sync> syncList = syncReader.findByTypeWithInterestWithLocation(user.getSyncType(), categories, user.getLocation);
        return syncList.stream().map( sync -> SyncInfoResponseDto.of(
                sync.getId(),
                sync.getSyncType(),
                syncCategoryReader.findBySyncId().getCategory.getType,
                sync.getImage(),
                particiationManager.countParticipationBySyncId(sync.getId()),
                sync.getMember_max(),
                sync.getSyncName(),
                sync.getLocation(),
                sync.getDate()
        )).toList();
    }
}
