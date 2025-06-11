package kr.co.yourplanet.online.infra.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.co.yourplanet.core.enums.AuthPurpose;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeData;
import kr.co.yourplanet.online.business.auth.repository.AuthCodeRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisAuthCodeRepository implements AuthCodeRepository {

    private static final String PREFIX = "auth-code:";
    private static final String PURPOSE = "purpose";
    private static final String CODE = "code";
    private static final String MEMBER_ID = "memberId";

    private static final long TTL_TIME = 300L;  // 5분

    private final RedisRepository redisRepository;

    @Override
    public void save(AuthPurpose purpose, String destination, String code, long memberId) {
        String key = getKey(destination);

        Map<String, Object> data = new HashMap<>();
        data.put(PURPOSE, purpose.name());
        data.put(CODE, code);
        data.put(MEMBER_ID, memberId);

        redisRepository.saveAllHash(key, data, TTL_TIME);
    }

    @Override
    public Optional<AuthCodeData> get(String destination) {
        String key = getKey(destination);

        return redisRepository.getAllHashValue(key)
                .flatMap(this::mapToVerificationCodeData);
    }

    @Override
    public void delete(String destination) {
        redisRepository.delete(getKey(destination));
    }

    private String getKey(String destination) {
        return PREFIX + destination;
    }

    private Optional<AuthCodeData> mapToVerificationCodeData(Map<Object, Object> map) {
        try {
            AuthPurpose purpose = AuthPurpose.valueOf(map.get(PURPOSE).toString());
            String code = map.get(CODE).toString();
            long memberId = Long.parseLong(map.get(MEMBER_ID).toString());

            return Optional.of(new AuthCodeData(purpose, code, memberId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
