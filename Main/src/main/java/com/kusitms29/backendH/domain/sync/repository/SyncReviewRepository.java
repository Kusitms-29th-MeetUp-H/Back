package com.kusitms29.backendH.domain.sync.repository;

import com.kusitms29.backendH.domain.sync.entity.SyncReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SyncReviewRepository extends JpaRepository<SyncReview, Long> {
    List<SyncReview> findAllBySyncId(Long syncId);
}
