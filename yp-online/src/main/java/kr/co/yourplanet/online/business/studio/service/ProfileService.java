package kr.co.yourplanet.online.business.studio.service;

import kr.co.yourplanet.online.business.studio.dto.ProfileInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioDetailInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioRegisterForm;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {
    ProfileInfo getStudioProfile(Long memberId);

    void upsertAndDeleteStudio(Long memberId, StudioRegisterForm studio, MultipartFile profileImageFile);

    Page<StudioBasicInfo> searchStudios(List<String> categories, String keywordType, String keyword, Integer minPrice, Integer maxPrice, Integer pageNumber, Integer pageSize);

    StudioDetailInfo getStudioDetail(Long studioId, Long requestMemberId);

}
