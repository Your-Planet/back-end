package kr.co.yourplanet.online.business.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordForm {

    @Schema(description = "인증 후 발급 받은 토큰")
    @NotBlank
    private String authToken;

    @Schema(description = "새로운 비밀번호")
    @NotBlank
    private String newPassword;
}
