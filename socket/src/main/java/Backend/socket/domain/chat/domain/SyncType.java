package Backend.socket.domain.chat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum SyncType {

    //일회성, 지속성, 내친소
    ONETIME("일회성"),
    LONGTIME("지속성"),
    FROM_FRIEND("내친소");

    private final String stringSyncType;

//    public static SyncType getEnumFROMStringSyncType(String stringSyncType) {
//        return Arrays.stream(values())
//                .filter(syncType -> syncType.stringSyncType.equals(stringSyncType))
//                .findFirst()
//                .orElseThrow(() -> new InvalidValueException(INVALID_SYNC_TYPE));
//    }

}
