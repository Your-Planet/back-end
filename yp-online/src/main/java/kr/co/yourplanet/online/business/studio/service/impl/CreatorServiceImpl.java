package kr.co.yourplanet.online.business.studio.service.impl;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.studio.Category;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.studio.dao.StudioBasicDao;
import kr.co.yourplanet.online.business.studio.dto.PriceInfoWithoutPrice;
import kr.co.yourplanet.online.business.studio.dto.ProfileInfo;
import kr.co.yourplanet.online.business.studio.dto.CreatorBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.CreatorDetailInfo;
import kr.co.yourplanet.online.business.studio.repository.StudioRepositoryCustom;
import kr.co.yourplanet.online.business.studio.service.CreatorService;
import kr.co.yourplanet.online.business.studio.service.PriceService;
import kr.co.yourplanet.online.business.studio.service.ProfileService;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {

    private final MemberRepository memberRepository;
    private final ProfileService profileService;
    private final PriceService priceService;
    private final StudioRepositoryCustom studioRepositoryCustom;

    @Override
    public Page<CreatorBasicInfo> searchStudios(Long requestMemberId, List<String> inputCategories, String keywordType, String keyword, Integer minPrice, Integer maxPrice, Integer inputPageNumber, Integer inputPageSize) {
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

        List<StudioBasicDao> studioBasicDaoList = studioRepositoryCustom.findStudioBasicsWithFilters(categories, toonName, description, instagramUsername, minPrice, maxPrice, pageable);

        Map<Long, CreatorBasicInfo> studioBasicSearchMap = new HashMap<>();

        // 카테고리 Grouping
        for (StudioBasicDao dao : studioBasicDaoList) {
            Long creatorId = dao.getCreatorId();
            CreatorBasicInfo creatorBasicInfo = studioBasicSearchMap.get(creatorId);
            if (creatorBasicInfo == null) {
                List<String> categoryList = new ArrayList<>();
                categoryList.add(dao.getCategoryCode());
                studioBasicSearchMap.put(creatorId, CreatorBasicInfo.builder()
                        .id(dao.getCreatorId())
                        .name(dao.getToonName())
                        .description(dao.getDescription())
                        .profileImageUrl(profileService.getProfileImageUrl(dao.getProfileImageFileId()))
                        .instagramUsername(dao.getInstagramUsername())
                        .categories(categoryList)
                        .build());
            } else {
                creatorBasicInfo.getCategories().add(dao.getCategoryCode());
            }
        }

        return new PageImpl<>(new ArrayList<>(studioBasicSearchMap.values()), pageable, 0);
    }

    @Override
    public CreatorDetailInfo getStudioDetail(Long creatorId, Long requestMemberId) {
        Member requestMember = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 회원 정보가 존재하지 않습니다.", false));

        // 광고주만 작가 상세 조회 가능
        if (!MemberType.SPONSOR.equals(requestMember.getMemberType())) {
            throw new BusinessException(StatusCode.FORBIDDEN, "작가 조회는 광고주만 가능해요.", false);
        }

        ProfileInfo profile = profileService.getStudioProfileByMemberId(creatorId);
        PriceInfoWithoutPrice priceInfoWithoutPrice = priceService.getPriceInfoWithoutPriceByMemberId(creatorId);

        return CreatorDetailInfo.builder()
                .id(creatorId)
                .profile(profile)
                .price(priceInfoWithoutPrice)
                .build();
    }
}
