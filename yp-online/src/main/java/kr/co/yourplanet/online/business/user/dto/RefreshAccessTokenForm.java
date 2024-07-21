package kr.co.yourplanet.online.business.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshAccessTokenForm {

    @NotBlank
    String accessToken;

    @NotBlank
    String refreshToken;

}
