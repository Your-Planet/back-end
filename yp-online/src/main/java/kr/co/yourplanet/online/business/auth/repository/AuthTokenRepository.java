package kr.co.yourplanet.online.business.auth.repository;

import java.util.Optional;

import kr.co.yourplanet.core.enums.AuthPurpose;

public interface AuthTokenRepository {

    void save(AuthPurpose tokenPurpose, String token, long memberId);

    Optional<Long> getMemberId(AuthPurpose tokenPurpose, String token);

    void delete(AuthPurpose tokenPurpose, String token);
}
