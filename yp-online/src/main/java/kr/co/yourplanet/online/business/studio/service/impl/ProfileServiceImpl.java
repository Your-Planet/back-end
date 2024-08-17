package kr.co.yourplanet.online.business.studio.service.impl;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.studio.Category;
import kr.co.yourplanet.core.entity.studio.PortfolioCategoryMap;
import kr.co.yourplanet.core.entity.studio.PortfolioLink;
import kr.co.yourplanet.core.entity.studio.Studio;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.instagram.repository.InstagramMediaRepository;
import kr.co.yourplanet.online.business.studio.dao.StudioBasicDao;
import kr.co.yourplanet.online.business.studio.dto.ProfileInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioRegisterForm;
import kr.co.yourplanet.online.business.studio.repository.CategoryRepository;
import kr.co.yourplanet.online.business.studio.repository.PortfolioCategoryMapRepository;
import kr.co.yourplanet.online.business.studio.repository.PortfolioLinkRepository;
import kr.co.yourplanet.online.business.studio.repository.StudioRepository;
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

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final FileManageUtil fileManageUtil;
    private final StudioRepository studioRepository;
    private final MemberRepository memberRepository;
    private final PortfolioCategoryMapRepository portfolioCategoryMapRepository;
    private final PortfolioLinkRepository portfolioLinkRepository;
    private final CategoryRepository categoryRepository;
    private final InstagramMediaRepository instagramMediaRepository;
    private final FileProperties fileProperties;

    public ProfileInfo getStudio(Long memberId) {
        Optional<Studio> optionalStudio = studioRepository.findById(memberId);
        if (!optionalStudio.isPresent()) {
            throw new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false);
        }
        Studio studio = optionalStudio.get();
        List<InstagramMedia> medias = studio.getPortfolioLinkUrls();

        return ProfileInfo.builder()
                .name(studio.getToonName())
                .description(studio.getDescription())
                .categories(studio.getCategoryTypes())
                .portfolios(medias)
                .profileImageUrl(fileProperties.getBaseUrl() + studio.getProfileImageUrl())
                .build();
    }

    @Transactional
    public void upsertAndDeleteStudio(Long memberId, StudioRegisterForm studioDto, MultipartFile profileImageFile) {
        if (studioDto.isDuplicateIds()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "중복된 포트폴리오 ID가 포함되어 있습니다.", false);
        }

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 회원 정보가 존재하지 않습니다.", false));
        Optional<Studio> optionalStudio = studioRepository.findById(memberId);
        Studio studio = optionalStudio.orElseGet(() -> Studio.builder()
                .member(member)
                .toonName(studioDto.getName())
                .description(studioDto.getDescription())
                .build());

        List<Category> categories = categoryRepository.findAllByCategoryCodeIn(studioDto.getCategories());

        studio.updateStudioNameAndDescription(studioDto.getName(), studioDto.getDescription());
        studio = studioRepository.save(studio);

        List<PortfolioLink> portfolioLinkList = new ArrayList<>();
        List<PortfolioCategoryMap> portfolioCategoryMapList = new ArrayList<>();

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
            portfolioCategoryMapList.add(PortfolioCategoryMap.builder()
                    .category(category)
                    .studio(studio)
                    .build());
        }

        LocalDateTime now = LocalDateTime.now();
        portfolioLinkRepository.saveAll(portfolioLinkList);
        portfolioCategoryMapRepository.saveAll(portfolioCategoryMapList);

        if (optionalStudio.isPresent()) {
            portfolioLinkRepository.deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(studio, now, now);
            portfolioCategoryMapRepository.deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(studio, now, now);
        }

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
    public Page<StudioBasicInfo> searchStudios(List<String> inputCategories, String keywordType, String keyword, Integer minPrice, Integer maxPrice, Integer inputPageNumber, Integer inputPageSize) {
        List<Category> categories = new ArrayList<>();
        String toonName = "";
        String description = "";
        String instagramUsername = "";

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
                        .instagramUsername(studioBasicDao.getInstagramUsername())
                        .categories(categoryList)
                        .build());
            } else {
                studioBasicInfo.getCategories().add(studioBasicDao.getCategoryCode());
            }
        }

        return new PageImpl<>(new ArrayList<>(studioBasicSearchMap.values()), pageable, 0);
    }
}
