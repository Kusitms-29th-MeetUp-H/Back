package com.kusitms29.backendH.api.sync.controller;

import com.kusitms29.backendH.api.sync.service.SyncService;
import com.kusitms29.backendH.api.sync.service.dto.request.SyncCreateRequestDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncSaveResponseDto;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/sync")
@RestController
public class SyncManageController {
    private final SyncService syncService;
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createSync(@UserId Long userId,
                                                         @RequestPart MultipartFile image,
                                                         @RequestPart SyncCreateRequestDto requestDto) {
        SyncSaveResponseDto responseDto = syncService.createSync(userId, image, requestDto);
        return SuccessResponse.ok(responseDto);
    }

}
