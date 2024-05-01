package Backend.socket.global.error.socketException;

import Backend.socket.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.messaging.MessageDeliveryException;

@Getter
public class BusinessException extends MessageDeliveryException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
