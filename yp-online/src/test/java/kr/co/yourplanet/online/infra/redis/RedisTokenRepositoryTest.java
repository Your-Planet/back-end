package kr.co.yourplanet.online.infra.redis;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.yourplanet.core.enums.AuthPurpose;
import kr.co.yourplanet.support.template.IntegrationTest;

class RedisTokenRepositoryTest extends IntegrationTest {

    @Autowired
    private RedisTokenRepository redisTokenRepository;

    @Test
    @DisplayName("[성공] 토큰 저장 및 조회에 성공한다.")
    void save_and_get_token() {
        // given
        AuthPurpose purpose = AuthPurpose.PASSWORD_RESET;
        String token = "sample-token-123";
        long memberId = 1L;

        // when
        redisTokenRepository.save(purpose, token, memberId);
        Optional<Long> result = redisTokenRepository.getMemberId(purpose, token);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(memberId);
    }
}