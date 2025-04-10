package kr.co.yourplanet.online.business.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshTokenForm {

    @NotBlank
    String accessToken;
    @NotBlank
    String refreshToken;

}
