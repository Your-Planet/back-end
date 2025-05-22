package kr.co.yourplanet.online.business.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import kr.co.yourplanet.core.enums.AuthenticationMethod;
import kr.co.yourplanet.core.validation.annotation.ValidEnum;

public record VerificationCodeSendForm(

        @Schema(description = "인증 수단")
        @ValidEnum(enumClass = AuthenticationMethod.class)
        AuthenticationMethod method,

        @Schema(description = "이메일")
        @Email
        String email,

        @Schema(description = "전화번호", format = "숫자만")
        Long tel
) {
}
