package kr.co.yourplanet.core.enums;

public enum StatusCode {

    OK(200, "OK"),

    INVALID_INPUT_VALUE(400, "입력값이 유효하지 않습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),
    NOT_FOUND(404, "리스소를 찾지 못하였습니다."),
    UNAUTHORIZED(401, "권한이 없는 요청입니다."),
    FORBIDDEN(403, "권한이 없는 요청입니다."),
    
    INTERNAL_SERVER_ERROR(500, "서버에 문제가 발생하였습니다."),
    NOT_IMPLEMENTED(501, "서버에 문제가 발생하였습니다.");

    final int code;
    final String message;

    StatusCode(int statusCode, String message) {
        this.code = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
