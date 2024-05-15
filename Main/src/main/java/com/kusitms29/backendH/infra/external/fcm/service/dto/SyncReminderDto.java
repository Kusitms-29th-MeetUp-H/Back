package com.kusitms29.backendH.infra.external.fcm.service.dto;

import com.kusitms29.backendH.infra.external.fcm.MessageTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SyncReminderDto {
    private String id;
    private String name;
    private String syncName;
    private MessageTemplate template;
}
