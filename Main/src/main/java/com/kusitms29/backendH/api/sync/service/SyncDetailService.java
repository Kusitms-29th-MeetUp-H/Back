package com.kusitms29.backendH.api.sync.service;


import com.kusitms29.backendH.api.sync.service.dto.response.SyncDetailResponseDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncGraphResponseDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncReviewResponseDto;
import com.kusitms29.backendH.domain.chat.service.RoomAppender;
import com.kusitms29.backendH.domain.sync.entity.Participation;
import com.kusitms29.backendH.domain.sync.service.ParticipationManager;
import com.kusitms29.backendH.domain.sync.service.ParticipationReader;
import com.kusitms29.backendH.domain.sync.entity.Sync;
import com.kusitms29.backendH.domain.sync.entity.SyncType;
import com.kusitms29.backendH.domain.sync.service.SyncManager;
import com.kusitms29.backendH.domain.sync.service.SyncReader;
import com.kusitms29.backendH.domain.sync.entity.SyncReview;
import com.kusitms29.backendH.domain.sync.service.SyncReviewReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.global.error.ErrorCode;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import com.kusitms29.backendH.global.error.exception.ListException;
import com.kusitms29.backendH.infra.utils.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kusitms29.backendH.domain.chat.entity.Room.createRoom;
import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_SYNC_TYPE;

@Service
@RequiredArgsConstructor
public class SyncDetailService {
    private final SyncReader syncReader;
    private final UserReader userReader;
    private final ParticipationManager participationManager;
    private final SyncManager syncManager;
    private final ParticipationReader participationReader;
    private final ListUtils listUtils;
    private final RoomAppender roomAppender;
    public SyncDetailResponseDto getSyncDetail(Long syncId){
        Sync sync = syncReader.findById(syncId);
        User user = userReader.findByUserId(sync.getUser().getId());
        int count = participationManager.countParticipationBySyncId(syncId);
        Boolean isFull = syncManager.validateJoinRoom(sync,count);
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
                    sync.getUserIntro(),
                    isFull
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
                    sync.getUserIntro(),
                    isFull
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
        List<SyncInfoResponseDto> syncInfoResponseDtos = listUtils.getListByTake(syncList.stream()
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
        return ListException.throwIfEmpty(syncInfoResponseDtos, () -> new EntityNotFoundException(ErrorCode.SYNC_NOT_FOUND));
    }
    private final SyncReviewReader syncReviewReader;
    public List<SyncReviewResponseDto> getSyncReviewList(Long syncId, int take){
        List<SyncReview> syncReviews = syncReviewReader.findAllBySyncId(syncId);
        List<SyncReviewResponseDto> syncReviewResponseDtos = syncReviews.stream().
                map(syncReview -> SyncReviewResponseDto.of(
                        syncReview.getUser().getProfile(),
                        syncReview.getUser().getUserName(),
                        syncReview.getUser().getUniversity(),
                        syncReview.getContent(),
                        syncReview.getCreatedAt()
                )).toList();
        return listUtils.getListByTake(syncReviewResponseDtos, take);
    }
    public void joinSync(Long userId, Long syncId){
        Participation.createParticipation(User.from(userId), Sync.from(syncId));
        int count = participationManager.countParticipationBySyncId(syncId);
        Boolean isPossible = syncManager.validateCreateRoom(syncReader.findById(syncId),count);
        List<User> userList = participationReader.findAllBySyncId(syncId).stream().map(participation -> participation.getUser()).toList();
        roomAppender.createRoom(userList,isPossible,syncId);
    }
}

