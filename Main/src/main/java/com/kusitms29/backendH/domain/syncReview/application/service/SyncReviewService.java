package com.kusitms29.backendH.domain.syncReview.application.service;

import com.kusitms29.backendH.domain.sync.domain.Sync;
import com.kusitms29.backendH.domain.sync.domain.service.SyncReader;
import com.kusitms29.backendH.domain.syncReview.application.service.dto.SyncReviewResponseDto;
import com.kusitms29.backendH.domain.syncReview.domain.SyncReview;
import com.kusitms29.backendH.domain.syncReview.domain.service.SyncReviewReader;
import com.kusitms29.backendH.domain.user.domain.service.UserReader;
import com.kusitms29.backendH.global.error.exception.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncReviewService {
    private final ListUtils listUtils;
    private final SyncReviewReader syncReviewReader;
    public List<SyncReviewResponseDto> getSyncReviewList(Long syncId, int take){
        List<SyncReview> syncReviews = syncReviewReader.findAllBySyncId(syncId);
        List<SyncReviewResponseDto> syncReviewResponseDtos = syncReviews.stream().
                map(syncReview -> SyncReviewResponseDto.of(
                syncReview.getUser().getProfile(),
                syncReview.getUser().getUserName(),
                syncReview.getUser().getUniversity(),
                syncReview.getContent(),
                syncReview.getCreatedAt()
        )).toList();
        return listUtils.getListByTake(syncReviewResponseDtos, take);
    }
}
