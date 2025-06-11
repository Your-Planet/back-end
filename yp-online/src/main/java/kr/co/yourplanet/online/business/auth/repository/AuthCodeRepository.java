package kr.co.yourplanet.online.business.auth.repository;

import java.util.Optional;

import kr.co.yourplanet.core.enums.AuthPurpose;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeData;

public interface AuthCodeRepository {

    void save(AuthPurpose purpose, String destination, String code, long memberId);

    Optional<AuthCodeData> get(String destination);

    void delete(String destination);
}
