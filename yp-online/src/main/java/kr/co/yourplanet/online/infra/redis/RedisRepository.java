package kr.co.yourplanet.online.infra.redis;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    /**
     * 저장
     */
    public void save(String key, String value, long ttlInSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttlInSeconds));
    }

    public void saveHash(String key, String hashKey, Object value, long ttlInSeconds) {
        objectRedisTemplate.opsForHash().put(key, hashKey, value);
        objectRedisTemplate.expire(key, Duration.ofSeconds(ttlInSeconds));
    }

    public void saveAllHash(String key, Map<String, Object> data, long ttlInSeconds) {
        objectRedisTemplate.boundHashOps(key).putAll(data);
        objectRedisTemplate.expire(key, Duration.ofSeconds(ttlInSeconds));
    }

    /**
     * 조회
     */
    public Optional<String> getValue(String key) {
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(key));
    }

    public Optional<Object> getHashValue(String key, String hashKey) {
        return Optional.ofNullable(objectRedisTemplate.opsForHash().get(key, hashKey));
    }

    public Optional<Map<Object, Object>> getAllHashValue(String key) {
        return Optional.ofNullable(objectRedisTemplate.boundHashOps(key).entries());
    }

    /**
     * 삭제
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void deleteHash(String key) {
        objectRedisTemplate.delete(key);
    }

    /**
     * 검증
     */
    public boolean hasKey(String key) {
        if (key == null) {
            return false;
        }

        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    public boolean hasHashKey(String key) {
        if (key == null) {
            return false;
        }

        return Boolean.TRUE.equals(objectRedisTemplate.hasKey(key));
    }
}
