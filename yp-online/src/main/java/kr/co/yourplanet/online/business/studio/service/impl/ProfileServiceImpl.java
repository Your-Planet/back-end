package kr.co.yourplanet.online.business.studio.service.impl;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.studio.Category;
import kr.co.yourplanet.core.entity.studio.PortfolioLink;
import kr.co.yourplanet.core.entity.studio.Studio;
import kr.co.yourplanet.core.entity.studio.StudioCategoryMap;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.instagram.repository.InstagramMediaRepository;
import kr.co.yourplanet.online.business.studio.dao.StudioBasicDao;
import kr.co.yourplanet.online.business.studio.dto.*;
import kr.co.yourplanet.online.business.studio.repository.CategoryRepository;
import kr.co.yourplanet.online.business.studio.repository.StudioCategoryMapRepository;
import kr.co.yourplanet.online.business.studio.repository.PortfolioLinkRepository;
import kr.co.yourplanet.online.business.studio.repository.StudioRepository;
import kr.co.yourplanet.online.business.studio.service.PriceService;
import kr.co.yourplanet.online.business.studio.service.ProfileService;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.common.util.FileManageUtil;
import kr.co.yourplanet.online.common.util.FileUploadResult;
import kr.co.yourplanet.online.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final FileManageUtil fileManageUtil;
    private final StudioRepository studioRepository;
    private final MemberRepository memberRepository;
    private final StudioCategoryMapRepository studioCategoryMapRepository;
    private final PortfolioLinkRepository portfolioLinkRepository;
    private final CategoryRepository categoryRepository;
    private final InstagramMediaRepository instagramMediaRepository;
    private final FileProperties fileProperties;

    private final PriceService priceService;

    public ProfileInfo getStudioProfileByMemberId(Long memberId) {
        Studio studio = studioRepository.findByMemberId(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false));

        return getProfileInfoByStudio(studio);
    }

    public ProfileInfo getStudioProfileByStudioId(Long studioId) {
        Studio studio = studioRepository.findById(studioId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false));

        return getProfileInfoByStudio(studio);
    }

    private ProfileInfo getProfileInfoByStudio(Studio studio) {
        List<InstagramMedia> medias = studio.getPortfolioLinkUrls();

        List<PortfolioInfo> portfolioInfoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(medias)) {
            portfolioInfoList = medias.stream().map(PortfolioInfo::new)
                    .collect(Collectors.toList());
        }

        return ProfileInfo.builder()
                .id(studio.getId())
                .name(studio.getToonName())
                .description(studio.getDescription())
                .categories(studio.getCategoryTypes())
                .portfolios(portfolioInfoList)
                .profileImageUrl(fileProperties.getBaseUrl() + studio.getProfileImageUrl())
                .instagramUsername(studio.getMember().getInstagramUsername())
                .build();
    }

    @Transactional
    public void upsertAndDeleteStudio(Long memberId, StudioRegisterForm studioDto, MultipartFile profileImageFile) {
        if (studioDto.isDuplicateIds()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "중복된 포트폴리오 ID가 포함되어 있습니다.", false);
        }

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 회원 정보가 존재하지 않습니다.", false));
        Optional<Studio> optionalStudio = studioRepository.findByMemberId(memberId);
        Studio studio = optionalStudio.orElseGet(() -> Studio.builder()
                .member(member)
                .toonName(studioDto.getName())
                .description(studioDto.getDescription())
                .build());

        List<Category> categories = categoryRepository.findAllByCategoryCodeIn(studioDto.getCategories());

        studio.updateStudioNameAndDescription(studioDto.getName(), studioDto.getDescription());
        studio = studioRepository.save(studio);

        List<PortfolioLink> portfolioLinkList = new ArrayList<>();
        List<StudioCategoryMap> studioCategoryMapList = new ArrayList<>();

        for (String id : studioDto.getPortfolioIds()) {
            InstagramMedia media = instagramMediaRepository.findById(id).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 미디어 ID가 포함되어 있습니다.", false));
            portfolioLinkList.add(PortfolioLink.builder()
                    .media(media)
                    .studio(studio)
                    .build());
        }

        if (categories.size() != studioDto.getCategories().size()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "존재하지 않는 카테고리 코드가 포함되어 있습니다.", false);
        }
        for (Category category : categories) {
            studioCategoryMapList.add(StudioCategoryMap.builder()
                    .category(category)
                    .studio(studio)
                    .build());
        }

        if (optionalStudio.isPresent()) {
            portfolioLinkRepository.deleteAllByStudio(studio);
            studioCategoryMapRepository.deleteAllByStudio(studio);
        }

        portfolioLinkRepository.saveAll(portfolioLinkList);
        studioCategoryMapRepository.saveAll(studioCategoryMapList);


        // 프로필 이미지 저장 부분
        if (profileImageFile != null && !profileImageFile.isEmpty()) {
            fileManageUtil.validateImageFile(profileImageFile);
            // 모든 스튜디오 프로필 저장 로직이 완료된 후 프로필 이미지 저장처리
            // 기존에 존재하는 프로필 이미지 삭제
            if (StringUtils.hasText(studio.getProfileImagePath())) {
                fileManageUtil.deleteFile(studio.getProfileImagePath());
                studio.updateProfileImage("", "");
            }
            // 프로필 이미지 파일시스템 저장
            FileUploadResult uploadResult = fileManageUtil.uploadFile(profileImageFile, FileType.PROFILE_IMAGE);
            studio.updateProfileImage(uploadResult.getFilePath(), uploadResult.getFileUrl());
        }
    }

    @Override
    public Page<StudioBasicInfo> searchStudios(Long requestMemberId, List<String> inputCategories, String keywordType, String keyword, Integer minPrice, Integer maxPrice, Integer inputPageNumber, Integer inputPageSize) {
        List<Category> categories = new ArrayList<>();
        String toonName = "";
        String description = "";
        String instagramUsername = "";

        Member requestMember = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 회원 정보가 존재하지 않습니다.", false));

        // 광고주만 작가 상세 조회 가능
        if (!MemberType.SPONSOR.equals(requestMember.getMemberType())) {
            throw new BusinessException(StatusCode.FORBIDDEN, "작가 조회는 광고주만 가능해요.", false);
        }

        int pageNumber = (inputPageNumber != null) ? inputPageNumber : 0;
        int pageSize = (inputPageSize != null) ? inputPageSize : 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "예산 최솟값은 최댓값보다 작아야 합니다.", false);
        }

        if (!CollectionUtils.isEmpty(inputCategories)) {
            for (String categoryCode : inputCategories) {
                categories.add(Category.builder()
                        .categoryCode(categoryCode)
                        .build());
            }
        }

        // switch문 리펙토링 진행할지
        // keywordType Enum으로 생설할 것 인지
        switch (keywordType != null ? keywordType : "") {
            case "toonName":
                toonName = keyword;
                break;
            case "description":
                description = keyword;
                break;
            case "instagramUsername":
                instagramUsername = keyword;
                break;
            default:
                break;
        }


        List<StudioBasicDao> studioBasicDaoList = studioRepository.findStudioBasicsWithFilters(categories, toonName, description, instagramUsername, minPrice, maxPrice, pageable);

        Map<Long, StudioBasicInfo> studioBasicSearchMap = new HashMap<>();

        // 카테고리 Grouping
        for (StudioBasicDao studioBasicDao : studioBasicDaoList) {
            Long studioId = studioBasicDao.getId();
            StudioBasicInfo studioBasicInfo = studioBasicSearchMap.get(studioId);
            if (studioBasicInfo == null) {
                List<String> categoryList = new ArrayList<>();
                categoryList.add(studioBasicDao.getCategoryCode());
                studioBasicSearchMap.put(studioId, StudioBasicInfo.builder()
                        .id(studioBasicDao.getId())
                        .name(studioBasicDao.getToonName())
                        .description(studioBasicDao.getDescription())
                        .profileImageUrl(fileProperties.getBaseUrl() + studioBasicDao.getProfileImageUrl())
                        .instagramUsername(studioBasicDao.getInstagramUsername())
                        .categories(categoryList)
                        .build());
            } else {
                studioBasicInfo.getCategories().add(studioBasicDao.getCategoryCode());
            }
        }

        return new PageImpl<>(new ArrayList<>(studioBasicSearchMap.values()), pageable, 0);
    }

    @Override
    public StudioDetailInfo getStudioDetail(Long studioId, Long requestMemberId) {
        Member requestMember = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 회원 정보가 존재하지 않습니다.", false));

        // 광고주만 작가 상세 조회 가능
        if (!MemberType.SPONSOR.equals(requestMember.getMemberType())) {
            throw new BusinessException(StatusCode.FORBIDDEN, "작가 조회는 광고주만 가능해요.", false);
        }

        ProfileInfo profile = getStudioProfileByStudioId(studioId);
        PriceInfoWithoutPrice priceInfoWithoutPrice = priceService.getPriceInfoWithoutPriceByStudioId(studioId);

        return StudioDetailInfo.builder()
                .id(studioId)
                .profile(profile)
                .price(priceInfoWithoutPrice)
                .build();
    }
}
