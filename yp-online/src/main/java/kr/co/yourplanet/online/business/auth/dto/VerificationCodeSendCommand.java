package kr.co.yourplanet.online.business.auth.dto;

import kr.co.yourplanet.core.enums.AuthenticationMethod;
import kr.co.yourplanet.core.enums.AuthPurpose;

public record VerificationCodeSendCommand(
        AuthPurpose purpose,
        AuthenticationMethod method,
        String destination,
        String verificationCode,
        long memberId
) {
}
