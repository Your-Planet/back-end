package kr.co.yourplanet.online.infra.web.payment;

import java.util.Arrays;

public enum TossPaymentErrorCode {
    INVALID_REQUEST,
    INVALID_API_KEY,
    NOT_FOUND_TERMINAL_ID,
    INVALID_AUTHORIZE_AUTH,
    INVALID_UNREGISTERED_SUBMALL,
    UNAPPROVED_ORDER_ID,
    UNAUTHORIZED_KEY,
    REJECT_CARD_COMPANY,
    FORBIDDEN_REQUEST,
    INCORRECT_BASIC_AUTH_FORMAT,
    NOT_FOUND_PAYMENT;

    public static boolean isValueIn(String codeName) {
        return Arrays.stream(values())
                .anyMatch(code -> code.name().equals(codeName));
    }
}
