package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.Profile;
import kr.co.yourplanet.core.entity.studio.ProfileCategoryMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ProfileCategoryMapRepository extends JpaRepository<ProfileCategoryMap, Long>{
    void deleteAllByProfileAndCreateDateBeforeAndUpdateDateBefore(Profile profile, LocalDateTime localDateTime, LocalDateTime localDateTime2);

    void deleteAllByProfile(Profile profile);
}
