package com.kusitms29.backendH.domain.sync.service;

import com.kusitms29.backendH.domain.sync.repository.ParticipationRepository;
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
