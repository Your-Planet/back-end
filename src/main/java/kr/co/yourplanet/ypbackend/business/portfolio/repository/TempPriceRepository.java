package kr.co.yourplanet.ypbackend.business.portfolio.repository;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.TempPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempPriceRepository extends JpaRepository<TempPrice, Long> {
}
