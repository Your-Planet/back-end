package kr.co.yourplanet.ypbackend.business.portfolio.repository;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.PortfolioCategoryMap;
import kr.co.yourplanet.ypbackend.business.portfolio.domain.PortfolioCategoryMapKey;
import kr.co.yourplanet.ypbackend.business.portfolio.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PortfolioCategoryMapRepository extends JpaRepository<PortfolioCategoryMap, PortfolioCategoryMapKey>{
    void deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(Studio studio, LocalDateTime localDateTime, LocalDateTime localDateTime2);
}
