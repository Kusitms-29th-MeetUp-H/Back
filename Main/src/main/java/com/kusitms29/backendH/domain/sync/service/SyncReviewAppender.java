package com.kusitms29.backendH.domain.sync.service;

import com.kusitms29.backendH.domain.sync.entity.SyncReview;
import com.kusitms29.backendH.domain.sync.repository.SyncReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SyncReviewAppender {
    private final SyncReviewRepository syncReviewRepository;
    @Transactional
    public SyncReview createReview(SyncReview syncReview){
        return syncReviewRepository.save(syncReview);
    }
}

