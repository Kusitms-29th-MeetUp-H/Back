package Backend.socket.domain.chat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;



@RequiredArgsConstructor
@Getter
public enum Platform {
    GOOGLE("google"),
    KAKAO("kakao"),
    WITHDRAW("withdraw");

    private final String stringPlatform;

//    public static Platform getEnumPlatformFromStringPlatform(String stringPlatform) {
//        return Arrays.stream(values())
//                .filter(platform -> platform.stringPlatform.equals(stringPlatform))
//                .findFirst()
//                .orElseThrow(() -> new InvalidValueException(INVALID_PLATFORM_TYPE));
//    }
}

