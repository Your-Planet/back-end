package kr.co.yourplanet.online.infra.redis;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.co.yourplanet.online.business.auth.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisVerificationCodeRepository implements VerificationCodeRepository {

    private static final String PREFIX = "auth-code:";
    private static final long TTL_TIME = 300L;  // 5분

    private final RedisRepository redisRepository;

    @Override
    public void save(String destination, String code) {
        redisRepository.save(getKey(destination), code, TTL_TIME);
    }

    @Override
    public Optional<String> get(String destination) {
        String key = getKey(destination);
        return redisRepository.getValue(key);
    }

    private String getKey(String destination) {
        return PREFIX + destination;
    }
}
