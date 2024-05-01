package Backend.socket.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MessageSuccessResponse<T> {
    private int code;
    private String messageType;
    private T data;

    public static <T> MessageSuccessResponse<?> of(MessageSuccessCode successCode, T data) {
        return MessageSuccessResponse.builder()
                .code(successCode.getCode())
                .messageType(successCode.getMessageType())
                .data(data)
                .build();
    }
}
