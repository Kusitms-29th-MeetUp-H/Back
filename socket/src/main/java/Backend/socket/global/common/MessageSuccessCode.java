package Backend.socket.global.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MessageSuccessCode {
    RECEIVED(200, "received"),
    MESSAGE(200, "messageDetail"),
    CHATLIST(200, "chatList"),
    SEARCH(200, "search");

    private final int code;
    private final String messageType;
}
