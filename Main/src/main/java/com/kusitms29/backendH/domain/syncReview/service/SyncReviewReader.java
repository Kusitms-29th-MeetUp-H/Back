package com.kusitms29.backendH.domain.syncReview.service;

import com.kusitms29.backendH.domain.syncReview.domain.SyncReview;
import com.kusitms29.backendH.domain.syncReview.repository.SyncReviewRepository;
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
