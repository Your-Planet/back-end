package kr.co.yourplanet.online.infra.redis;

import java.time.Duration;

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
        return redisTemplate.hasKey(key);
    }

    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }
}
