package com.kusitms29.backendH.domain.sync.application.service;

import com.kusitms29.backendH.domain.participation.domain.service.ParticipationManager;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncDetailResponseDto;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.domain.service.SyncReader;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.domain.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SyncDetailService {
    private final SyncReader syncReader;
    private final UserReader userReader;
    private final ParticipationManager participationManager;
    public SyncDetailResponseDto getSyncDetail(Long syncId){
        Sync sync = syncReader.findById(syncId);
        User user = userReader.findByUserId(sync.getUser().getId());
        return SyncDetailResponseDto.of(
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
    }
}
