package kr.co.yourplanet.core.entity.member;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Password {
    private static final String PASSWORD_DELIMITER = "$";
    private static final String PASSWORD_FORMAT_REGEX = "^(\\d+)\\$(.+)$";

    private int length;
    private String encryptedPassword;

    // 비밀번호 -> 결합된 문자열
    @Override
    public String toString() {
        return length + PASSWORD_DELIMITER + encryptedPassword;
    }

    // 결합된 문자열 -> 비밀번호
    public static Password fromString(String combined) {
        validatePasswordForm(combined);

        String[] parts = combined.split("\\" + PASSWORD_DELIMITER, 2);
        int length = Integer.parseInt(parts[0]);
        String encryptedPassword = parts[1];

        return new Password(length, encryptedPassword);
    }

    public static Password create(String rawPassword, String encryptedPassword) {
        if (rawPassword == null || encryptedPassword == null) {
            throw new IllegalArgumentException("비밀번호는 null일 수 없습니다.");
        }
        return new Password(rawPassword.length(), encryptedPassword);
    }

    public static void validatePasswordForm(String combined) {
        if (combined == null || !combined.matches(PASSWORD_FORMAT_REGEX)) {
            throw new IllegalArgumentException("유효하지 않은 비밀번호 형식: " + combined);
        }
    }
}
