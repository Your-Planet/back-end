package kr.co.yourplanet.online.business.portfolio.repository;

import kr.co.yourplanet.online.business.portfolio.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
