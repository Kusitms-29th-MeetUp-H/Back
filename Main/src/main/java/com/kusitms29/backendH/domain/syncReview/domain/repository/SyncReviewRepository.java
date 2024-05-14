package com.kusitms29.backendH.domain.syncReview.domain.repository;

import com.kusitms29.backendH.domain.syncReview.domain.SyncReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SyncReviewRepository extends JpaRepository<SyncReview, Long> {
    List<SyncReview> findAllBySyncId(Long syncId);
}
