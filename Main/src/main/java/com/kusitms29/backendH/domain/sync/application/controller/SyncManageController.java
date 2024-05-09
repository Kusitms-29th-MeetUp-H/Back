package com.kusitms29.backendH.domain.sync.application.controller;

import com.kusitms29.backendH.domain.clova.map.GeoLocation;
import com.kusitms29.backendH.domain.clova.map.GeoLocationService;
import com.kusitms29.backendH.domain.sync.application.controller.dto.request.SyncInfoRequest2Dto;
import com.kusitms29.backendH.domain.sync.application.controller.dto.request.SyncInfoRequestDto;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncAssociateInfoResponseDto;
import com.kusitms29.backendH.domain.sync.application.controller.dto.response.SyncInfoResponseDto;
import com.kusitms29.backendH.domain.sync.application.service.SyncManageService;
import com.kusitms29.backendH.domain.user.ip.IpService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/sync")
@RestController
public class SyncManageController {
    private final SyncManageService syncManageService;
    private final IpService ipService;
    private final GeoLocationService geoLocationService;
    @GetMapping("/recommend")
    public ResponseEntity<SuccessResponse<?>> recommendSync(@RequestParam(name = "userId") Long userId, HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        String clientIp = ipService.getClientIpAddress(request);
        GeoLocation geoLocation = geoLocationService.getGeoLocation(clientIp);
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.recommendSync(userId, clientIp);
        return SuccessResponse.ok(syncInfoResponseDtos);
    }
    @PostMapping("/friend")
    public ResponseEntity<SuccessResponse<?>> friendSync(@RequestBody SyncInfoRequestDto syncInfoRequestDto) {
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.friendSync(syncInfoRequestDto.type());
        List<SyncInfoResponseDto> dtos = syncManageService.getSyncInfoByTake(syncInfoResponseDtos, syncInfoRequestDto.take());
        return SuccessResponse.ok(dtos);
    }
    @PostMapping("/{sync}")
    public ResponseEntity<SuccessResponse<?>> searchSync(@PathVariable(name = "sync") String sync, @RequestBody SyncInfoRequestDto syncInfoRequestDto) {
        List<SyncInfoResponseDto> syncInfoResponseDtos = syncManageService.searchSync(sync, syncInfoRequestDto.syncType(),syncInfoRequestDto.type());
        List<SyncInfoResponseDto> dtos = syncManageService.getSyncInfoByTake(syncInfoResponseDtos, syncInfoRequestDto.take());
        return SuccessResponse.ok(dtos);
    }
    @GetMapping("/associate")
    public ResponseEntity<SuccessResponse<?>> associateSync(@RequestParam(name = "take",defaultValue = "0") int take) {
        List<SyncAssociateInfoResponseDto> syncInfoResponseDtos = syncManageService.associateSync();
        List<SyncAssociateInfoResponseDto> dtos = syncManageService.getSyncInfoByTake(syncInfoResponseDtos, take);
        return SuccessResponse.ok(dtos);
    }
}
