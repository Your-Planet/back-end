package kr.co.yourplanet.online.business.portfolio.repository;

import kr.co.yourplanet.core.entity.portfolio.PortfolioCategoryMap;
import kr.co.yourplanet.core.entity.portfolio.PortfolioCategoryMapKey;
import kr.co.yourplanet.core.entity.portfolio.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PortfolioCategoryMapRepository extends JpaRepository<PortfolioCategoryMap, PortfolioCategoryMapKey>{
    void deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(Studio studio, LocalDateTime localDateTime, LocalDateTime localDateTime2);
}
