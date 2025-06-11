package kr.co.yourplanet.online.business.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import kr.co.yourplanet.core.enums.AuthMethod;
import kr.co.yourplanet.core.enums.AuthPurpose;
import kr.co.yourplanet.core.validation.annotation.ValidEnum;

public record AuthCodeSendForm(

        @Schema(description = "인증 목적")
        @ValidEnum(enumClass = AuthPurpose.class)
        AuthPurpose purpose,

        @Schema(description = "인증 수단")
        @ValidEnum(enumClass = AuthMethod.class)
        AuthMethod method,

        @Schema(description = "이메일")
        @Email
        String email,

        @Schema(description = "전화번호", format = "숫자", example = "01012345678")
        @Pattern(regexp = "^[0-9]+$", message = "전화번호는 숫자만 입력해야 합니다.")
        String tel
) {
}
