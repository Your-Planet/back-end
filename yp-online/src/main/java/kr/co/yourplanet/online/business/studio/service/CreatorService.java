package kr.co.yourplanet.online.business.studio.service;

import kr.co.yourplanet.online.business.studio.dto.CreatorBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.CreatorDetailInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CreatorService {

    Page<CreatorBasicInfo> searchStudios(Long requestMemberId, List<String> categories, String keywordType, String keyword, Integer minPrice, Integer maxPrice, Integer pageNumber, Integer pageSize);

    CreatorDetailInfo getStudioDetail(Long creatorId, Long requestMemberId);

}
