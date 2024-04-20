package kr.co.yourplanet.ypbackend.business.portfolio.repository;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.PortfolioLink;
import kr.co.yourplanet.ypbackend.business.portfolio.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PortfolioLinkRepository extends JpaRepository<PortfolioLink, Long>{
    void deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(Studio studio, LocalDateTime localDateTime, LocalDateTime localDateTime2);
}
