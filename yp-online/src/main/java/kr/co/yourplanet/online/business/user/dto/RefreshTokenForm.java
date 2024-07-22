package kr.co.yourplanet.online.business.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshTokenForm {

    String accessToken;
    String refreshToken;

}
