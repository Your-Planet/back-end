package kr.co.yourplanet.ypbackend.business.portfolio.repository;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findByMemberId(Long memberId);
}
