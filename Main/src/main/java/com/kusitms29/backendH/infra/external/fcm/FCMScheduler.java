package com.kusitms29.backendH.infra.external.fcm;

import com.kusitms29.backendH.infra.external.fcm.service.PushNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FCMScheduler {
    private final PushNotificationService pushNotificationService;
    //@Scheduled(cron = "0 00 09 * * *") //오전 9시
    //@Scheduled(initialDelay = 0, fixedDelay = 7000)
    public void sendSyncReminder() {
        log.info("=== SYNREMINDER START ===");
        pushNotificationService.sendSyncReminder();
        log.info("=== SYNREMINDER END ===");
    }
}
