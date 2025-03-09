package kr.co.yourplanet.online.common.util;

public class MaskingUtil {

    private static final String MASKED_PASSWORD = "******";

    public static String maskRRN(String rrn) {
        if (rrn == null || !rrn.matches("\\d{6}-\\d{7}")) {
            return null;
        }
        return rrn.substring(0, 8) + MASKED_PASSWORD;
    }

    public static String getMaskedPassword() {
        return MASKED_PASSWORD;
    }
}