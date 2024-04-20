package kr.co.yourplanet.ypbackend.business.portfolio.service;

import kr.co.yourplanet.ypbackend.business.portfolio.dto.StudioBasicInfo;

public interface ProfileService {
    StudioBasicInfo getStudio(Long memberId);

    void updateStudio(Long memberId, StudioBasicInfo studio);

    void createStudio(Long memberId, StudioBasicInfo studio);
}
