package com.kusitms29.backendH.api.sync.service;


import com.kusitms29.backendH.api.sync.service.dto.request.SyncCreateRequestDto;
import com.kusitms29.backendH.api.sync.service.dto.request.SyncInfoRequestDto;
import com.kusitms29.backendH.api.sync.service.dto.response.*;
import com.kusitms29.backendH.domain.category.entity.Category;
import com.kusitms29.backendH.domain.category.entity.Type;
import com.kusitms29.backendH.domain.category.service.CategoryReader;
import com.kusitms29.backendH.domain.category.service.UserCategoryManager;
import com.kusitms29.backendH.domain.category.service.UserCategoryReader;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import com.kusitms29.backendH.domain.sync.service.ParticipationManager;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.service.SyncAppender;
import com.kusitms29.backendH.domain.sync.service.SyncReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.category.entity.UserCategory;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import com.kusitms29.backendH.global.error.exception.NotAllowedException;
import com.kusitms29.backendH.infra.config.AwsS3Service;
import com.kusitms29.backendH.infra.external.SeoulAddressClient;
import com.kusitms29.backendH.infra.utils.ListUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms29.backendH.domain.category.entity.Type.getEnumTypeFromStringType;
import static com.kusitms29.backendH.domain.sync.entity.SyncType.FROM_FRIEND;
import static com.kusitms29.backendH.domain.sync.entity.SyncType.getEnumFROMStringSyncType;
import static com.kusitms29.backendH.global.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncService {
    private final SyncReader syncReader;
    private final SyncAppender syncAppender;
    private final UserReader userReader;
    private final UserCategoryReader userCategoryReader;
    private final UserCategoryManager userCategoryManager;
    private final ParticipationManager participationManager;
    private final ListUtils listUtils;
    private final AwsS3Service awsS3Service;
    private final CategoryReader categoryReader;
    private final SeoulAddressClient seoulAddressClient;

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

    public SyncSaveResponseDto createSync(Long userId, MultipartFile file, SyncCreateRequestDto requestDto) {
        User user = userReader.findByUserId(userId);

        if(requestDto.getUserIntro().length() > 50) {
            throw new NotAllowedException(USER_INTRO_NOT_ALLOWED);
        }
        if(requestDto.getSyncIntro().length() > 500) {
            throw new NotAllowedException(SYNC_INTRO_NOT_ALLOWED);
        }

        SyncType enumSyncType = SyncType.getEnumFROMStringSyncType(requestDto.getSyncType());

        if(requestDto.getSyncName().length() > 15) {
            throw new NotAllowedException(SYNC_NAME_NOT_ALLOWED);
        }

        String image = awsS3Service.uploadImage(file);

        LocalDateTime oneTimeLocalDateTime = null;
        if(requestDto.getDate() != null && !requestDto.getDate().isEmpty()) {
            oneTimeLocalDateTime= parseToLocalDateTime(requestDto.getDate()); //2023-04-13 15:30
        }

        String regularDay = null;
        if(requestDto.getRegularDay() != null && !requestDto.getRegularDay().isEmpty()) {
            regularDay = requestDto.getRegularDay();
        }

        LocalDateTime regularLocalDateTime = null;
        if(requestDto.getRoutineDate() != null && !requestDto.getRoutineDate().isEmpty()) {
            regularLocalDateTime = parseToLocalDateTime(requestDto.getRoutineDate()); //2023-04-13 15:30
        }

        LocalTime regularLocalTime = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if(requestDto.getRegularTime() != null && !requestDto.getRegularTime().isEmpty()) { //15:30
            regularLocalTime = LocalTime.parse(requestDto.getRegularTime(), formatter);
        }

        if(requestDto.getMember_min() < 3) {
            throw new NotAllowedException(SYNC_MIN_NOT_ALLOWED);
        }
        if(requestDto.getMember_max() > 30) {
            throw new NotAllowedException(SYNC_MAX_NOT_ALLOWED);
        }

        Type enumType = Type.getEnumTypeFromStringType(requestDto.getType());

        Category detailCategory = categoryReader.findByName(requestDto.getDetailType());
        if(!detailCategory.getType().getStringType().equals(requestDto.getType())) {
            throw new InvalidValueException(INVALID_PARENT_CHILD_CATEGORY);
        }

        Sync newSync = syncAppender.save(
                Sync.createSync(
                        user,
                        requestDto.getUserIntro(),
                        requestDto.getSyncIntro(),
                        enumSyncType,
                        requestDto.getSyncName(),
                        image,
                        requestDto.getLocation(),
                        oneTimeLocalDateTime,
                        regularDay,
                        regularLocalTime,
                        regularLocalDateTime,
                        requestDto.getMember_min(),
                        requestDto.getMember_max(),
                        enumType,
                        requestDto.getDetailType())
        );

        return SyncSaveResponseDto.of(
                newSync.getId(),
                newSync.getUserIntro(),
                newSync.getSyncIntro(),
                newSync.getSyncType(),
                newSync.getSyncName(),
                newSync.getImage(),
                newSync.getLocation(),
                newSync.getDate(),
                newSync.getRegularDay(),
                newSync.getRegularTime(),
                newSync.getRoutineDate(),
                newSync.getMember_min(),
                newSync.getMember_max(),
                newSync.getType(),
                newSync.getDetailType()
        );
    }

    private LocalDateTime parseToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, formatter);
    }

    public List<String> getSeoulAddresses() {
        List<String> nameList = new ArrayList<>();
        SeoulAddressResponse seoulAddressResponse = seoulAddressClient.calloutSeoulAddressAPI();

        seoulAddressResponse.getRegcodes().forEach(result -> {
            String address = result.getName().replace("특별", "");
            nameList.add(address);
        });

        return nameList;
    }
}

