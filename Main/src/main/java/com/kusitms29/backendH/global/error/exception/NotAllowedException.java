package com.kusitms29.backendH.global.error.exception;

import com.kusitms29.backendH.global.error.ErrorCode;

public class NotAllowedException extends BusinessException {

    public NotAllowedException() {
        super(ErrorCode.METHOD_NOT_ALLOWED);
    }

    public NotAllowedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
