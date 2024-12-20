package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.TempPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TempPriceRepository extends JpaRepository<TempPrice, Long> {

    Optional<TempPrice> findByMemberId(Long memberId);

}
