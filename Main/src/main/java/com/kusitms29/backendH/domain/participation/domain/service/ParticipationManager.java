package com.kusitms29.backendH.domain.participation.domain.service;

import com.kusitms29.backendH.domain.participation.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationManager {
    private final ParticipationRepository participationRepository;
    public int countParticipationBySyncId(Long syncId){
        return participationRepository.countBySyncId(syncId);
    }
}
