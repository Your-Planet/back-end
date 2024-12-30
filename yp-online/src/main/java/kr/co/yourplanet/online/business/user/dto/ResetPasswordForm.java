package kr.co.yourplanet.online.business.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordForm {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String tel;
    @NotBlank
    private String newPassword;
}
