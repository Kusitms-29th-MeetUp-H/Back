package com.kusitms29.backendH.api.sync.controller;

import com.kusitms29.backendH.api.sync.service.SyncService;
import com.kusitms29.backendH.api.sync.service.dto.request.SyncCreateRequestDto;
import com.kusitms29.backendH.api.sync.service.dto.response.SyncSaveResponseDto;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/sync")
@RestController
public class SyncManageController {
    private final SyncService syncService;
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createSync(@UserId Long userId,
                                                         @RequestPart(required = false) MultipartFile image,
                                                         @RequestPart SyncCreateRequestDto requestDto) {
        SyncSaveResponseDto responseDto = syncService.createSync(userId, image, requestDto);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/seoul-address")
    public ResponseEntity<SuccessResponse<?>> getSeoulAddresses() {
        List<String> responseDto = syncService.getSeoulAddresses();
        return SuccessResponse.ok(responseDto);
    }

}
