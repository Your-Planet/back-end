package kr.co.yourplanet.online.business.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record VerificationCodeCheckForm(

        @Schema(description = "인증 요청 대상. 이메일 또는 전화번호 형식", example = "user@example.com")
        @NotBlank(message = "인증 요청 대상은 비어있을 수 없습니다.")
        String destination,

        @Schema(description = "인증 번호")
        @NotBlank(message = "인증 번호는 비어있을 수 없습니다.")
        String verificationCode
) {
}
