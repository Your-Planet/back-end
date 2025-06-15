package kr.co.yourplanet.online.common.exception;

import kr.co.yourplanet.core.enums.StatusCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message) {
        super(StatusCode.BAD_REQUEST, message, false);
    }
}
