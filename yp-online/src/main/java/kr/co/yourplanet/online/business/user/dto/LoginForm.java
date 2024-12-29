package kr.co.yourplanet.online.business.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class LoginForm {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
