package kr.co.yourplanet.online.common.util;

public class MaskingUtil {

    public static String maskRRN(String rrn) {
        if (rrn == null || rrn.length() != 14 || rrn.charAt(6) != '-') {
            return null;
        }

        String prefix = rrn.substring(0, 8);
        String maskedSuffix = "******";

        return prefix + maskedSuffix;
    }

    public static String maskPassword(int passwordLength) {
        if (passwordLength <= 0) {
            return "";
        }

        return "*".repeat(passwordLength);
    }
}
