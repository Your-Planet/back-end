package kr.co.yourplanet.online.business.studio.service;

import kr.co.yourplanet.online.business.studio.dto.StudioBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioBasicSearch;
import kr.co.yourplanet.online.business.studio.dto.StudioRegisterForm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProfileService {
    StudioBasicInfo getStudio(Long memberId);

    void upsertAndDeleteStudio(Long memberId, StudioRegisterForm studio);

    Page<StudioBasicSearch> searchStudios(List<String> categories, String keywordType, String keyword, Integer minPrice, Integer maxPrice, Integer pageNumber, Integer pageSize);

}
