package com.kusitms29.backendH.domain.syncReview.application.service;

import com.kusitms29.backendH.domain.syncReview.application.service.dto.SyncReviewResponseDto;
import com.kusitms29.backendH.domain.syncReview.domain.SyncReview;
import com.kusitms29.backendH.domain.syncReview.domain.service.SyncReviewReader;
import com.kusitms29.backendH.global.common.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncReviewService {
    private final Util util;
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
        return util.getListByTake(syncReviewResponseDtos, take);
    }
}
