<<<<<<<< HEAD:src/main/java/kr/co/yourplanet/ypbackend/common/enums/StatusCode.java
package kr.co.yourplanet.ypbackend.common.enums;
========
package kr.co.yourplanet.ypbackend.common.ENUM;
>>>>>>>> f70981d (feat: register form 수정):src/main/java/kr/co/yourplanet/ypbackend/common/ENUM/StatusCode.java

public enum StatusCode {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

    final int statusCode;
    final String code;

    StatusCode(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }

}
