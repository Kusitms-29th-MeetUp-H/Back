package com.kusitms29.backendH.domain.fcm.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FCMScheduler {
    private final SyncReminderService syncReminderService;

    //@Scheduled(cron = "0 00 09 * * *") //오전 9시
    public void sendSyncReminder() {
        log.info("=== SYNREMINDER START ===");
        syncReminderService.sendSyncReminder();
        log.info("=== SYNREMINDER END ===");
    }
}
