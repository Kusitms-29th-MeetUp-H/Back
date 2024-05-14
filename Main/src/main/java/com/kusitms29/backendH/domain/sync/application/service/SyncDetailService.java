package com.kusitms29.backendH.domain.sync.application.service;

import com.kusitms29.backendH.domain.participation.domain.Participation;
import com.kusitms29.backendH.domain.participation.domain.service.ParticipationManager;
import com.kusitms29.backendH.domain.participation.domain.service.ParticipationReader;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.GraphElement;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncDetailResponseDto;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncGraphResponseDto;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.domain.SyncType;
import com.kusitms29.backendH.domain.sync.domain.service.SyncManager;
import com.kusitms29.backendH.domain.sync.domain.service.SyncReader;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.domain.service.UserReader;
import com.kusitms29.backendH.global.error.ErrorCode;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import com.kusitms29.backendH.global.error.exception.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_SYNC_TYPE;

@Service
@RequiredArgsConstructor
public class SyncDetailService {
    private final SyncReader syncReader;
    private final UserReader userReader;
    private final ParticipationManager participationManager;
    private final SyncManager syncManager;
    private final ParticipationReader participationReader;
    private final SyncManageService syncManageService;
    public SyncDetailResponseDto getSyncDetail(Long syncId){
        Sync sync = syncReader.findById(syncId);
        User user = userReader.findByUserId(sync.getUser().getId());
        if (sync.getSyncType() == SyncType.ONETIME) {
            return SyncDetailResponseDto.oneTimeOf(
                    sync.getSyncName(),
                    sync.getImage(),
                    sync.getSyncType(),
                    sync.getType(),
                    sync.getSyncIntro(),
                    sync.getDate(),
                    sync.getLocation(),
                    participationManager.countParticipationBySyncId(sync.getId()),
                    sync.getMember_max(),
                    user.getProfile(),
                    user.getUserName(),
                    user.getUniversity(),
                    sync.getUserIntro()
            );
        } else if (sync.getSyncType() == SyncType.LONGTIME) {
            return SyncDetailResponseDto.longTimeOf(
                    sync.getSyncName(),
                    sync.getImage(),
                    sync.getSyncType(),
                    sync.getType(),
                    sync.getSyncIntro(),
                    sync.getRegularDay(),
                    sync.getRegularTime(),
                    sync.getDate(),
                    sync.getLocation(),
                    participationManager.countParticipationBySyncId(sync.getId()),
                    sync.getMember_max(),
                    user.getProfile(),
                    user.getUserName(),
                    user.getUniversity(),
                    sync.getUserIntro()
            );
        } else {
            throw new InvalidValueException(INVALID_SYNC_TYPE);
        }
    }
    public SyncGraphResponseDto getSyncDetailGraph(Long syncId, String graph){
        List<Participation> participations = participationReader.findAllBySyncId(syncId);
        SyncGraphResponseDto graphElements = syncManager.createGraphElementList(participations, graph);
        return graphElements;
    }
    public List<SyncInfoResponseDto> getSyncListBySameDateAndSameLocation(Long syncId, int take){
        Sync csync = syncReader.findById(syncId);
        List<Sync> syncList= syncReader.findAllByLocationAndDate(csync.getLocation(), csync.getDate());
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.getSyncInfoByTake(syncList.stream()
                .filter(sync -> !sync.getId().equals(syncId))
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
        )).toList(), take);
        return ListUtils.throwIfEmpty(syncInfoResponseDtos, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
}
