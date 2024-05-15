package com.kusitms29.backendH.application.sync.controller;

import com.kusitms29.backendH.application.sync.service.SyncDetailService;
import com.kusitms29.backendH.application.sync.service.dto.response.SyncDetailResponseDto;
import com.kusitms29.backendH.application.sync.service.dto.response.SyncGraphResponseDto;
import com.kusitms29.backendH.application.sync.service.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.domain.syncReview.application.service.dto.SyncReviewResponseDto;
import com.kusitms29.backendH.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/api/sync/detail")
@RestController
public class SyncDetailController {
    private final SyncDetailService syncDetailService;
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> syncDetail(@RequestParam(name = "syncId") Long syncId){
        SyncDetailResponseDto syncDetailResponseDto = syncDetailService.getSyncDetail(syncId);
        return SuccessResponse.ok(syncDetailResponseDto);
    }
    @GetMapping("/{graph}")
    public ResponseEntity<SuccessResponse<?>> syncDetailGraph(@RequestParam(name = "syncId") Long syncId, @PathVariable(name = "graph") String graph){
        SyncGraphResponseDto syncGraphResponseDto = syncDetailService.getSyncDetailGraph(syncId, graph);
        return SuccessResponse.ok(syncGraphResponseDto);
    }
    @GetMapping("/recommend")
    public ResponseEntity<SuccessResponse<?>> getAnotherSync(@RequestParam(name = "syncId") Long syncId,@RequestParam(name = "take", defaultValue = "0") int take){
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncDetailService.getSyncListBySameDateAndSameLocation(syncId, take);
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getSyncReviewList(@RequestParam(name = "syncId") Long syncId, @RequestParam(name = "take",defaultValue = "0") int take){
        List<SyncReviewResponseDto> syncReviewResponseDtos = syncDetailService.getSyncReviewList(syncId, take);
        return SuccessResponse.ok(syncReviewResponseDtos);
    }
}

