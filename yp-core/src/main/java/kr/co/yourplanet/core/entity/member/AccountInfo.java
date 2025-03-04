package kr.co.yourplanet.core.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import kr.co.yourplanet.core.util.PasswordConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
public class AccountInfo {

    @Column(unique = true, nullable = false)
    private String email;

    @Convert(converter = PasswordConverter.class)
    @Column(nullable = false)
    private Password password;

    protected static AccountInfo create(String email, Password password) {
        return AccountInfo.builder()
                .email(email)
                .password(password)
                .build();
    }

    protected void updatePassword(Password newPassword) {
        this.password = newPassword;
    }
}
