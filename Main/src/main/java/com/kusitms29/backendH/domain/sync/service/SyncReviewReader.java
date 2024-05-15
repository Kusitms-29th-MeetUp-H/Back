package com.kusitms29.backendH.domain.sync.service;

import com.kusitms29.backendH.domain.sync.entity.SyncReview;
import com.kusitms29.backendH.domain.sync.repository.SyncReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncReviewReader {
    private final SyncReviewRepository syncReviewRepository;
    public List<SyncReview> findAllBySyncId(Long syncId){
        return syncReviewRepository.findAllBySyncId(syncId);
    }
}
