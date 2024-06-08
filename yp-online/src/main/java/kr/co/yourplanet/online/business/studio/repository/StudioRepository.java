package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Long>, StudioRepositoryCustom {
    Optional<Studio> findByMemberId(Long memberId);
}
