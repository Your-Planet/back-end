package kr.co.yourplanet.online.business.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class LoginForm {

    @Schema(defaultValue = "hongildong@gmail.com")
    @NotBlank
    private String email;

    @Schema(defaultValue = "password123@")
    @NotBlank
    private String password;
}
