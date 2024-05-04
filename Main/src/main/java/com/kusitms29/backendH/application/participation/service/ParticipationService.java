package com.kusitms29.backendH.application.participation.service;

import com.kusitms29.backendH.application.participation.controller.response.EnterSyncResponseDto;
import com.kusitms29.backendH.domain.participation.domain.Participation;
import com.kusitms29.backendH.domain.participation.repository.ParticipationRepository;
import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.repository.SyncRepository;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.ConflictException;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.global.error.exception.NotAllowedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.kusitms29.backendH.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ParticipationService {
    private final UserRepository userRepository;
    private final SyncRepository syncRepository;
    private final ParticipationRepository participationRepository;

    @Transactional
    public EnterSyncResponseDto enterSync(Long userId, Long syncId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        Sync sync = syncRepository.findById(syncId)
                .orElseThrow(() -> new EntityNotFoundException(SYNC_NOT_FOUND));

        Optional<Participation> existedParticipation = participationRepository.findByUserAndSync(user, sync);
        if(existedParticipation.isPresent()) {
            throw new ConflictException(DUPLICATE_PARTICIPATION);
        }

        if(sync.getMember_max() <= sync.getMember_cnt()) {
            throw new NotAllowedException(PARTICIPATION_NOT_ALLOWED);
        }
        Participation newParticipation = Participation.createParticipation(user, sync);
        participationRepository.save(newParticipation);

        List<Participation> syncList = participationRepository.findBySync(sync);
        int member_cnt = syncList.size();
        sync.updateMemberCnt(member_cnt);

        return EnterSyncResponseDto.of(userId, syncId);
    }


}
