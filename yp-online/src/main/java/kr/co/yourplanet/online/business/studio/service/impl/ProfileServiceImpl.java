package kr.co.yourplanet.online.business.studio.service.impl;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.studio.Category;
import kr.co.yourplanet.core.entity.studio.PortfolioLink;
import kr.co.yourplanet.core.entity.studio.Profile;
import kr.co.yourplanet.core.entity.studio.ProfileCategoryMap;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.instagram.repository.InstagramMediaRepository;
import kr.co.yourplanet.online.business.studio.dto.PortfolioInfo;
import kr.co.yourplanet.online.business.studio.dto.ProfileInfo;
import kr.co.yourplanet.online.business.studio.dto.ProfileRegisterForm;
import kr.co.yourplanet.online.business.studio.repository.CategoryRepository;
import kr.co.yourplanet.online.business.studio.repository.PortfolioLinkRepository;
import kr.co.yourplanet.online.business.studio.repository.ProfileCategoryMapRepository;
import kr.co.yourplanet.online.business.studio.repository.ProfileRepository;
import kr.co.yourplanet.online.business.studio.service.ProfileService;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.common.util.FileManageUtil;
import kr.co.yourplanet.online.common.util.FileUploadResult;
import kr.co.yourplanet.online.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final FileManageUtil fileManageUtil;
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final ProfileCategoryMapRepository profileCategoryMapRepository;
    private final PortfolioLinkRepository portfolioLinkRepository;
    private final CategoryRepository categoryRepository;
    private final InstagramMediaRepository instagramMediaRepository;
    private final FileProperties fileProperties;

    public ProfileInfo getStudioProfileByMemberId(Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "프로필 정보가 존재하지 않습니다.", false));

        return getProfileInfoByProfileEntity(profile);
    }

    private ProfileInfo getProfileInfoByProfileEntity(Profile profile) {
        List<InstagramMedia> medias = profile.getPortfolioLinkUrls();

        List<PortfolioInfo> portfolioInfoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(medias)) {
            portfolioInfoList = medias.stream().map(PortfolioInfo::new)
                    .collect(Collectors.toList());
        }

        return ProfileInfo.builder()
                .id(profile.getId())
                .name(profile.getToonName())
                .description(profile.getDescription())
                .categories(profile.getCategoryTypes())
                .portfolios(portfolioInfoList)
                .profileImageUrl(fileProperties.getBaseUrl() + profile.getProfileImageUrl())
                .instagramUsername(profile.getMember().getInstagramInfo().getInstagramUsername())
                .build();
    }

    @Transactional
    public void upsertAndDeleteProfile(Long memberId, ProfileRegisterForm profileDto, MultipartFile profileImageFile) {
        if (profileDto.isDuplicateIds()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "중복된 포트폴리오 ID가 포함되어 있습니다.", false);
        }

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 회원 정보가 존재하지 않습니다.", false));
        Optional<Profile> optionalProfile = profileRepository.findByMemberId(memberId);
        Profile profile = optionalProfile.orElseGet(() -> Profile.builder()
                .member(member)
                .toonName(profileDto.getName())
                .description(profileDto.getDescription())
                .build());

        List<Category> categories = categoryRepository.findAllByCategoryCodeIn(profileDto.getCategories());

        profile.updateNameAndDescription(profileDto.getName(), profileDto.getDescription());
        profile = profileRepository.save(profile);

        List<PortfolioLink> portfolioLinkList = new ArrayList<>();
        List<ProfileCategoryMap> profileCategoryMapList = new ArrayList<>();

        for (String id : profileDto.getPortfolioIds()) {
            InstagramMedia media = instagramMediaRepository.findById(id).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 미디어 ID가 포함되어 있습니다.", false));
            portfolioLinkList.add(PortfolioLink.builder()
                    .media(media)
                    .profile(profile)
                    .build());
        }

        if (categories.size() != profileDto.getCategories().size()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "존재하지 않는 카테고리 코드가 포함되어 있습니다.", false);
        }
        for (Category category : categories) {
            profileCategoryMapList.add(ProfileCategoryMap.builder()
                    .category(category)
                    .profile(profile)
                    .build());
        }

        if (optionalProfile.isPresent()) {
            portfolioLinkRepository.deleteAllByProfile(profile);
            profileCategoryMapRepository.deleteAllByProfile(profile);
        }

        portfolioLinkRepository.saveAll(portfolioLinkList);
        profileCategoryMapRepository.saveAll(profileCategoryMapList);


        // 프로필 이미지 저장 부분
        if (profileImageFile != null && !profileImageFile.isEmpty()) {
            fileManageUtil.validateImageFile(profileImageFile);
            // 모든 스튜디오 프로필 저장 로직이 완료된 후 프로필 이미지 저장처리
            // 기존에 존재하는 프로필 이미지 삭제
            if (StringUtils.hasText(profile.getProfileImagePath())) {
                fileManageUtil.deleteFile(profile.getProfileImagePath());
                profile.updateProfileImage("", "");
            }
            // 프로필 이미지 파일시스템 저장
            FileUploadResult uploadResult = fileManageUtil.uploadFile(profileImageFile, FileType.PROFILE_IMAGE);
            profile.updateProfileImage(uploadResult.getFilePath(), uploadResult.getFileUrl());
        }
    }
}
