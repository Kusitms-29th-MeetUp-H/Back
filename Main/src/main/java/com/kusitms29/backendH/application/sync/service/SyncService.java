package com.kusitms29.backendH.application.sync.service;


import com.kusitms29.backendH.application.sync.service.dto.request.SyncInfoRequestDto;
import com.kusitms29.backendH.application.sync.service.dto.response.SyncAssociateInfoResponseDto;
import com.kusitms29.backendH.application.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.domain.category.domain.Type;
import com.kusitms29.backendH.domain.participation.domain.service.ParticipationManager;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.service.SyncReader;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.domain.UserCategory;
import com.kusitms29.backendH.domain.user.domain.service.UserCategoryManager;
import com.kusitms29.backendH.domain.user.domain.service.UserCategoryReader;
import com.kusitms29.backendH.domain.user.domain.service.UserReader;
import com.kusitms29.backendH.infra.utils.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kusitms29.backendH.domain.category.domain.Type.getEnumTypeFromStringType;
import static com.kusitms29.backendH.domain.sync.entity.SyncType.FROM_FRIEND;
import static com.kusitms29.backendH.domain.sync.entity.SyncType.getEnumFROMStringSyncType;

@Service
@RequiredArgsConstructor
public class SyncService {
    private final SyncReader syncReader;
    private final UserReader userReader;
    private final UserCategoryReader userCategoryReader;
    private final UserCategoryManager userCategoryManager;
    private final ParticipationManager participationManager;
    private final ListUtils listUtils;
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
    public List<SyncInfoResponseDto> friendSync(SyncInfoRequestDto syncInfoRequestDto){
        List<Sync> syncList = syncReader.findAllBySyncTypeAndType(FROM_FRIEND, getEnumTypeFromStringType(syncInfoRequestDto.type()));
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncList.stream()
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
        return listUtils.getListByTake(syncInfoResponseDtos, syncInfoRequestDto.take());
    }
    public List<SyncAssociateInfoResponseDto> associateSync(SyncInfoRequestDto syncInfoRequestDto){
        List<Sync> syncList = syncReader.findAllByAssociateIsExist(getEnumFROMStringSyncType(syncInfoRequestDto.syncType()), getEnumTypeFromStringType(syncInfoRequestDto.type()));
        List<SyncAssociateInfoResponseDto> syncAssociateInfoResponseDtos = syncList.stream().map( sync -> SyncAssociateInfoResponseDto.of(
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
        return listUtils.getListByTake(syncAssociateInfoResponseDtos, syncInfoRequestDto.take());
    }
    public List<SyncInfoResponseDto> searchSync(SyncInfoRequestDto syncInfoRequestDto){
        List<Sync> syncList = syncReader.findAllBySyncTypeAndType(getEnumFROMStringSyncType(syncInfoRequestDto.syncType()), getEnumTypeFromStringType(syncInfoRequestDto.type()));

        List<SyncInfoResponseDto> syncInfoResponseDtos = syncList.stream().map( sync -> SyncInfoResponseDto.of(
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
        return listUtils.getListByTake(syncInfoResponseDtos, syncInfoRequestDto.take());
    }
}

