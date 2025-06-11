package kr.co.yourplanet.online.business.auth.dto;

import kr.co.yourplanet.core.enums.AuthMethod;
import kr.co.yourplanet.core.enums.AuthPurpose;

public record AuthCodeSendCommand(
        AuthPurpose purpose,
        AuthMethod method,
        String destination,
        String authCode,
        long memberId
) {
}
