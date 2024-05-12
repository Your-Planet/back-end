package kr.co.yourplanet.online.business.studio.service.impl;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.core.entity.studio.Category;
import kr.co.yourplanet.core.entity.studio.PortfolioCategoryMap;
import kr.co.yourplanet.core.entity.studio.PortfolioLink;
import kr.co.yourplanet.core.entity.studio.Studio;
import kr.co.yourplanet.online.business.studio.dto.StudioBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioResiterForm;
import kr.co.yourplanet.online.business.studio.repository.*;
import kr.co.yourplanet.online.business.studio.service.ProfileService;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final StudioRepository studioRepository;
    private final MemberRepository memberRepository;
    private final PortfolioCategoryMapRepository portfolioCategoryMapRepository;
    private final PortfolioLinkRepository portfolioLinkRepository;
    private final CategoryRepository categoryRepository;
    private final InstagramMediaRepository instagramMediaRepository;

    public StudioBasicInfo getStudio(Long memberId) {
        Optional<Studio> optionalStudio = studioRepository.findById(memberId);
        if (!optionalStudio.isPresent()) {
            throw new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false);
        }
        Studio studio = optionalStudio.get();
        List<InstagramMedia> medias = studio.getPortfolioLinkUrls();
        List<StudioBasicInfo.Portfolio> portfolios = new ArrayList<>();

        for (InstagramMedia media : medias) {
            portfolios.add(StudioBasicInfo.Portfolio.builder()
                    .id(media.getId())
                    .link(media.getMediaUrl())
                    .build());
        }

        return StudioBasicInfo.builder()
                .name(studio.getToonName())
                .description(studio.getDescription())
                .categories(studio.getCategoryTypes())
                .portfolios(portfolios)
                .build();
    }

    @Transactional
    public void upsertAndDeleteStudio(Long memberId, StudioResiterForm studioDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 회원 정보가 존재하지 않습니다.", false));
        Optional<Studio> optionalStudio = studioRepository.findById(memberId);
        Studio studio = optionalStudio.orElseGet(() -> Studio.builder()
                .member(member)
                .toonName(studioDto.getName())
                .description(studioDto.getDescription())
                .build());

        List<Category> categories = categoryRepository.findAllByCategoryCodeIn(studioDto.getCategories());

        List<PortfolioLink> portfolioLinkList = new ArrayList<>();
        List<PortfolioCategoryMap> portfolioCategoryMapList = new ArrayList<>();

        studio.updateStudioNameAndDescription(studioDto.getName(), studioDto.getDescription());
        studio = studioRepository.save(studio);

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
    }
}