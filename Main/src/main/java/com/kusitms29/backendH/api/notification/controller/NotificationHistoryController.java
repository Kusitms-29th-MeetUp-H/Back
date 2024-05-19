package com.kusitms29.backendH.api.notification.controller;

import com.kusitms29.backendH.api.notification.service.NotificationHistoryService;
import com.kusitms29.backendH.api.notification.service.dto.NotificationHistoryResponseDto;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/notification")
@RestController
public class NotificationHistoryController {

    private final NotificationHistoryService notificationHistoryService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getNotificationByTopCategory(@UserId Long userId, @RequestParam String topCategory) {
        List<NotificationHistoryResponseDto> responseDto = notificationHistoryService.getNotificationByTopCategory(userId, topCategory);
        return SuccessResponse.ok(responseDto);
    }

}
