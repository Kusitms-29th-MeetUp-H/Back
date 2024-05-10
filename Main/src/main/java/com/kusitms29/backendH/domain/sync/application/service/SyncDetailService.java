package com.kusitms29.backendH.domain.sync.application.service;

import com.kusitms29.backendH.domain.participation.domain.Participation;
import com.kusitms29.backendH.domain.participation.domain.service.ParticipationManager;
import com.kusitms29.backendH.domain.participation.domain.service.ParticipationReader;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.GraphElement;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncDetailResponseDto;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncGraphResponseDto;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.domain.service.SyncManager;
import com.kusitms29.backendH.domain.sync.domain.service.SyncReader;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.domain.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncDetailService {
    private final SyncReader syncReader;
    private final UserReader userReader;
    private final ParticipationManager participationManager;
    private final SyncManager syncManager;
    private final ParticipationReader participationReader;
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
    public SyncGraphResponseDto getSyncDetailGraph(Long syncId, String graph){
        List<Participation> participations = participationReader.findAllBySyncId(syncId);
        SyncGraphResponseDto graphElements = syncManager.createGraphElementList(participations, graph);
        return graphElements;
    }

}
