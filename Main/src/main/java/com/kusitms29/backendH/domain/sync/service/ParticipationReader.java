package com.kusitms29.backendH.domain.sync.service;

import com.kusitms29.backendH.domain.sync.entity.Participation;
import com.kusitms29.backendH.domain.sync.repository.ParticipationRepository;
import com.kusitms29.backendH.global.error.ErrorCode;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.global.error.exception.ListException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationReader {
    private final ParticipationRepository participationRepository;
    public List<Participation> findAllBySyncId(Long syncId){
        List<Participation> participationList = participationRepository.findAllBySyncId(syncId);
        return ListException.throwIfEmpty(participationList, () -> new EntityNotFoundException(ErrorCode.PARTICIPATION_NOT_FOUND));
    }
    public List<Participation> findAllByUserId(Long userId){
        List<Participation> participationList = participationRepository.findAllByUserId(userId);
        return ListException.throwIfEmpty(participationList, () -> new EntityNotFoundException(ErrorCode.PARTICIPATION_NOT_FOUND));
    }
}
