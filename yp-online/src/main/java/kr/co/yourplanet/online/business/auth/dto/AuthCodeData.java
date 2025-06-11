package kr.co.yourplanet.online.business.auth.dto;

import kr.co.yourplanet.core.enums.AuthPurpose;

public record AuthCodeData(
        AuthPurpose purpose,
        String code,
        long memberId
) {
}
