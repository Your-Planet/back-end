package kr.co.yourplanet.online.business.studio.service;

import kr.co.yourplanet.online.business.studio.dto.StudioBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioResiterForm;

public interface ProfileService {
    StudioBasicInfo getStudio(Long memberId);

    void upsertAndDeleteStudio(Long memberId, StudioResiterForm studio);
}
