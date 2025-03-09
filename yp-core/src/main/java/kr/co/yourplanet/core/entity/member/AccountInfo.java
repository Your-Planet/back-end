package kr.co.yourplanet.core.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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

    @Column(nullable = false)
    private String password;

    protected static AccountInfo create(String email, String password) {
        return AccountInfo.builder()
                .email(email)
                .password(password)
                .build();
    }

    protected void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
