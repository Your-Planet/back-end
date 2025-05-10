package kr.co.yourplanet.online.common.exception;

import kr.co.yourplanet.core.enums.StatusCode;

public class InternalServerErrorException extends BusinessException {

    public InternalServerErrorException(String message) {
        super(StatusCode.INTERNAL_SERVER_ERROR, message, true);
    }
}
