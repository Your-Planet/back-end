package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.PortfolioCategoryMap;
import kr.co.yourplanet.core.entity.studio.PortfolioCategoryMapKey;
import kr.co.yourplanet.core.entity.studio.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PortfolioCategoryMapRepository extends JpaRepository<PortfolioCategoryMap, PortfolioCategoryMapKey>{
    void deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(Studio studio, LocalDateTime localDateTime, LocalDateTime localDateTime2);
}
