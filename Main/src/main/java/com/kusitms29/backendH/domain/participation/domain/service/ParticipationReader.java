package com.kusitms29.backendH.domain.participation.domain.service;

import com.kusitms29.backendH.domain.participation.domain.Participation;
import com.kusitms29.backendH.domain.participation.repository.ParticipationRepository;
import com.kusitms29.backendH.global.error.ErrorCode;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.global.error.exception.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationReader {
    private final ParticipationRepository participationRepository;
    public List<Participation> findAllBySyncId(Long syncId){
        List<Participation> participationList = participationRepository.findBySyncId(syncId);
        return ListUtils.throwIfEmpty(participationList, () -> new EntityNotFoundException(ErrorCode.PARTICIPATION_NOT_FOUND));
    }
}
