package kr.co.yourplanet.online.infra.redis;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.co.yourplanet.core.enums.AuthPurpose;
import kr.co.yourplanet.online.business.auth.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisAuthTokenRepository implements AuthTokenRepository {

    private static final String PREFIX = "token:";
    private static final long TTL_TIME = 600L;  // 10분

    private final RedisRepository redisRepository;

    @Override
    public void save(AuthPurpose tokenPurpose, String token, long memberId) {
        redisRepository.save(getKey(tokenPurpose, token), Long.toString(memberId), TTL_TIME);
    }

    @Override
    public Optional<Long> getMemberId(AuthPurpose tokenPurpose, String token) {
        return redisRepository.getValue(getKey(tokenPurpose, token))
                .map(Long::parseLong);
    }

    private String getKey(AuthPurpose tokenPurpose, String token) {
        return PREFIX + tokenPurpose.name().toLowerCase() + ":" + token;
    }
}
