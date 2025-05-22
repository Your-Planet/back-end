package kr.co.yourplanet.online.business.auth.repository;

import java.util.Optional;

public interface VerificationCodeRepository {

    void save(String destination, String code);
    Optional<String> get(String destination);
}
