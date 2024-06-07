package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.Category;
import kr.co.yourplanet.online.business.studio.dao.StudioBasicDao;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudioRepositoryCustom {

    List<StudioBasicDao> findStudioBasicsWithFilters(List<Category> categories, String toonName, String description, String instagramUsername, Pageable pageable);
}
