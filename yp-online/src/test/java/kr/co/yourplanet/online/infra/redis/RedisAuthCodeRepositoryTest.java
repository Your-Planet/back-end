package kr.co.yourplanet.online.infra.redis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.yourplanet.core.enums.AuthPurpose;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeData;
import kr.co.yourplanet.support.template.IntegrationTest;

class RedisAuthCodeRepositoryTest extends IntegrationTest {

    @Autowired
    private RedisAuthCodeRepository redisVerificationCodeRepository;

    @Test
    @DisplayName("[성공] 인증번호 저장 및 조회에 성공한다.")
    void save_and_get_code() {
        // given
        String destination = "01012345678";
        String code = "123456";
        long memberId = 1L;
        AuthPurpose purpose = AuthPurpose.PASSWORD_RESET;

        // when
        redisVerificationCodeRepository.save(purpose, destination, code, memberId);

        // then
        Optional<AuthCodeData> result = redisVerificationCodeRepository.get(destination);

        assertThat(result).isPresent();
        assertThat(result)
                .get()
                .satisfies(data -> {
                    assertThat(data.purpose()).isEqualTo(purpose);
                    assertThat(data.code()).isEqualTo(code);
                    assertThat(data.memberId()).isEqualTo(memberId);
                });
    }

    @Test
    @DisplayName("[성공] 인증번호 삭제에 성공한다.")
    void delete_code() {
        // given
        String destination = "01012345678";
        redisVerificationCodeRepository.save(AuthPurpose.PASSWORD_RESET, destination, "654321", 1L);

        // when
        redisVerificationCodeRepository.delete(destination);
        Optional<AuthCodeData> result = redisVerificationCodeRepository.get(destination);

        // then
        assertThat(result).isEmpty();
    }
}