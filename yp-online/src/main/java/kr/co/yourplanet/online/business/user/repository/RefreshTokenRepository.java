package kr.co.yourplanet.online.business.user.repository;

import kr.co.yourplanet.core.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
