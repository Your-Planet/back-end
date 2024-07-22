package kr.co.yourplanet.online.business.user.dto;

import kr.co.yourplanet.core.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshTokenResult {

    RefreshTokenForm refreshTokenForm;
    private String message;
    private StatusCode statusCode;

}
