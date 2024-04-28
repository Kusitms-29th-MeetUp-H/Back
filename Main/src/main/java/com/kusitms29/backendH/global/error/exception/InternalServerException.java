package com.kusitms29.backendH.global.error.exception;


import com.kusitms29.backendH.global.error.ErrorCode;

public class InternalServerException extends BusinessException {
    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
