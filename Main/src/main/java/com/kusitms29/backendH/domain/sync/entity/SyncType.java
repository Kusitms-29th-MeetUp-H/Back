package com.kusitms29.backendH.domain.sync.entity;

import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms29.backendH.global.error.ErrorCode.INVALID_SYNC_TYPE;

@RequiredArgsConstructor
@Getter
public enum SyncType {

    //일회성, 지속성, 내친소
    ONETIME("일회성"),
    LONGTIME("지속성"),
    FROM_FRIEND("내친소");

    private final String stringSyncType;

    public static SyncType getEnumFROMStringSyncType(String stringSyncType) {
        if (stringSyncType == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(syncType -> syncType.stringSyncType.equals(stringSyncType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_SYNC_TYPE));
    }

}
