package kr.co.yourplanet.online.business.studio.service;

import kr.co.yourplanet.online.business.studio.dto.ProfileInfo;
import kr.co.yourplanet.online.business.studio.dto.ProfileRegisterForm;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ProfileInfo getStudioProfileByMemberId(Long memberId);

    void upsertAndDeleteProfile(Long memberId, ProfileRegisterForm studio, MultipartFile profileImageFile);

    String getProfileImageUrl(Long fileId);
}
