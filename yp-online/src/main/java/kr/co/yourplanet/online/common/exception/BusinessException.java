package kr.co.yourplanet.online.common.exception;

import kr.co.yourplanet.core.enums.StatusCode;

public class BusinessException extends RuntimeException {
    private final StatusCode statusCode;

    public BusinessException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }

    public BusinessException(StatusCode statusCode, String message, boolean isAppend) {
        super(isAppend ? statusCode.getMessage() + message : message);
        this.statusCode = statusCode;
    }

    public BusinessException(String message, StatusCode statusCode, boolean isAppend, Throwable cause) {
        super(isAppend ? statusCode.getMessage() + message : message, cause);
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

}
