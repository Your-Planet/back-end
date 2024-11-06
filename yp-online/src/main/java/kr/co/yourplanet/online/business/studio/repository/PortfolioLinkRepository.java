package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.PortfolioLink;
import kr.co.yourplanet.core.entity.studio.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PortfolioLinkRepository extends JpaRepository<PortfolioLink, Long> {
    void deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(Studio studio, LocalDateTime localDateTime, LocalDateTime localDateTime2);

    void deleteAllByStudio(Studio studio);
}
