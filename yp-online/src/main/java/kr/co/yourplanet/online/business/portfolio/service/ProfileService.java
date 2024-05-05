package kr.co.yourplanet.online.business.portfolio.service;

import kr.co.yourplanet.online.business.portfolio.dto.StudioBasicInfo;

public interface ProfileService {
    StudioBasicInfo getStudio(Long memberId);

    void updateStudio(Long memberId, StudioBasicInfo studio);

    void createStudio(Long memberId, StudioBasicInfo studio);
}
