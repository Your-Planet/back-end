package kr.co.yourplanet.online.business.auth.dto;

import kr.co.yourplanet.core.enums.AuthenticationMethod;

public record VerificationCodeSendCommand(
        AuthenticationMethod method,
        String destination,
        String verificationCode
) {
}
