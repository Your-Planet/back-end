package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.PortfolioLink;
import kr.co.yourplanet.core.entity.studio.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PortfolioLinkRepository extends JpaRepository<PortfolioLink, Long> {
    void deleteAllByProfileAndCreateDateBeforeAndUpdateDateBefore(Profile profile, LocalDateTime localDateTime, LocalDateTime localDateTime2);

    void deleteAllByProfile(Profile profile);
}
