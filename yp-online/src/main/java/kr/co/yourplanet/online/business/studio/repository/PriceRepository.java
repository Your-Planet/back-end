package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
