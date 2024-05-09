package com.kusitms29.backendH.domain.sync.application.service;

import com.kusitms29.backendH.domain.category.domain.Type;
import com.kusitms29.backendH.domain.participation.domain.service.ParticipationManager;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncAssociateInfoResponseDto;
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
    public List<SyncInfoResponseDto> recommendSync(Long userId, String clientIp){
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
    public List<SyncInfoResponseDto> friendSync(String type){
        List<Sync> syncList = syncReader.findAllBySyncTypeAndType(FROM_FRIEND, type);
        return syncList.stream()
                //음 이거보다 위에서 if문써서 하는게 더 가독성 있는듯
//                .filter(sync -> type == null || sync.getType().name().equals(type))
                .map( sync -> SyncInfoResponseDto.of(
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
    public List<SyncAssociateInfoResponseDto> associateSync(){
        List<Sync> syncList = syncReader.findAllByAssociateIsExist();
        return syncList.stream().map( sync -> SyncAssociateInfoResponseDto.of(
                sync.getId(),
                sync.getSyncType(),
                sync.getType(),
                sync.getImage(),
                participationManager.countParticipationBySyncId(sync.getId()),
                sync.getMember_max(),
                sync.getSyncName(),
                sync.getLocation(),
                sync.getDate(),
                sync.getAssociate()
        )).toList();
    }
    public List<SyncInfoResponseDto> searchSync(String syncTypeOrType, String syncType, String type){
        List<Sync> syncList = syncReader.findAllBySyncAndSyncTypeInAndTypeIn(syncTypeOrType, syncType, type);
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
    public <T> List<T> getSyncInfoByTake(List<T> dtos, int take) {
        if (take == 0 || take >= dtos.size()) {
            return dtos;
        } else {
            return dtos.subList(0, take);
        }
    }
}
