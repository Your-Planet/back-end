package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.StudioCategoryMap;
import kr.co.yourplanet.core.entity.studio.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface StudioCategoryMapRepository extends JpaRepository<StudioCategoryMap, Long>{
    void deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(Studio studio, LocalDateTime localDateTime, LocalDateTime localDateTime2);

    void deleteAllByStudio(Studio studio);
}
