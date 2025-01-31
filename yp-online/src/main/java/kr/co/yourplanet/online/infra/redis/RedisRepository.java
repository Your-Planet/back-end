package kr.co.yourplanet.online.infra.redis;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveHashWithTTL(String key, String hashKey, Object value, long ttlInSeconds) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        redisTemplate.expire(key, Duration.ofSeconds(ttlInSeconds));
    }

    public boolean hasKey(String key) {
        if (key == null) {
            return false;
        }

        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public Optional<Object> getHashValue(String key, String hashKey) {
        return Optional.ofNullable(redisTemplate.opsForHash().get(key, hashKey));
    }
}
