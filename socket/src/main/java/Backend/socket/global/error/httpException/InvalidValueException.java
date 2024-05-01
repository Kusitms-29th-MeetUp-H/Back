package Backend.socket.global.error.httpException;


import Backend.socket.global.error.ErrorCode;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
