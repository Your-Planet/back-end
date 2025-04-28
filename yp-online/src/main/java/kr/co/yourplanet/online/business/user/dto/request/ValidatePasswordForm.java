package kr.co.yourplanet.online.business.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ValidatePasswordForm(
        @Schema(example = "password123@")
        @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
        String password
) {
}
