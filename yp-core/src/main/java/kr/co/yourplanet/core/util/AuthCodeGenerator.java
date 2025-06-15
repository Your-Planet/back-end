package kr.co.yourplanet.core.util;

import java.security.SecureRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthCodeGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final int CODE_LENGTH = 6;

    public static String generate() {
        int code = random.nextInt((int) Math.pow(10, CODE_LENGTH));
        return String.format("%06d", code);
    }
}
