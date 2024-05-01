package Backend.socket.global.error.socketException;


import Backend.socket.global.error.ErrorCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
