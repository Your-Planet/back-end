package kr.co.yourplanet.online.business.user.dto.response;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.user.dto.request.RefreshTokenForm;
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
