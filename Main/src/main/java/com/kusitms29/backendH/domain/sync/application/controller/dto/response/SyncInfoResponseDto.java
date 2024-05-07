package com.kusitms29.backendH.domain.sync.application.controller.dto.response;

public record SyncInfoResponseDto(
        Long syncId,
        String type,
        String interest,
        String image,
        int userCnt,
        int totalCnt,
        String syncName,
        String location,
        String date
) {
    public static SyncInfoResponseDto of(Long syncId,
                                         String type,
                                         String interest,
                                         String image,
                                         int userCnt,
                                         int totalCnt,
                                         String syncName,
                                         String location,
                                         String date){
        return new SyncInfoResponseDto(syncId,type,interest,image,userCnt,totalCnt,syncName,location,date);
    }
}
