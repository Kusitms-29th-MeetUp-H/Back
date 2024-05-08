package com.kusitms29.backendH.domain.sync.application.service;

import com.kusitms29.backendH.domain.category.domain.Type;
import com.kusitms29.backendH.domain.participation.domain.service.ParticipationManager;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.domain.service.SyncReader;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.domain.UserCategory;
import com.kusitms29.backendH.domain.user.domain.service.UserCategoryManager;
import com.kusitms29.backendH.domain.user.domain.service.UserCategoryReader;
import com.kusitms29.backendH.domain.user.domain.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kusitms29.backendH.domain.sync.domain.SyncType.FROM_FRIEND;

@Service
@RequiredArgsConstructor
public class SyncManageService {
    private final SyncReader syncReader;
    private final UserReader userReader;
    private final UserCategoryReader userCategoryReader;
    private final UserCategoryManager userCategoryManager;
    private final ParticipationManager participationManager;
    public List<SyncInfoResponseDto> recommendSync(Long userId){
        User user = userReader.findByUserId(userId);
        List<UserCategory> userCategories = userCategoryReader.findAllByUserId(userId);
        List<Type> types = userCategoryManager.getTypeByUserCategories(userCategories);
        List<Sync> syncList = syncReader.findBySyncTypeWithTypesWithLocation(user.getSyncType(), types, user.getLocation());
        return syncList.stream().map( sync -> SyncInfoResponseDto.of(
                sync.getId(),
                sync.getSyncType(),
                sync.getType(),
                sync.getImage(),
                participationManager.countParticipationBySyncId(sync.getId()),
                sync.getMember_max(),
                sync.getSyncName(),
                sync.getLocation(),
                sync.getDate()
        )).toList();
    }
    public List<SyncInfoResponseDto> friendSync(){
        List<Sync> syncList = syncReader.findAllBySyncType(FROM_FRIEND);
        return syncList.stream().map( sync -> SyncInfoResponseDto.of(
                sync.getId(),
                sync.getSyncType(),
                sync.getType(),
                sync.getImage(),
                participationManager.countParticipationBySyncId(sync.getId()),
                sync.getMember_max(),
                sync.getSyncName(),
                sync.getLocation(),
                sync.getDate()
        )).toList();
    }
    public List<SyncInfoResponseDto> getSyncInfoByTake(List<SyncInfoResponseDto> dtos, int take){
        if(take == 0)
            return dtos;
        else
            return dtos.subList(0,take);
    }
}
