package kr.co.yourplanet.ypbackend.business.portfolio.service.Impl;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.Category;
import kr.co.yourplanet.ypbackend.business.portfolio.domain.PortfolioCategoryMap;
import kr.co.yourplanet.ypbackend.business.portfolio.domain.PortfolioLink;
import kr.co.yourplanet.ypbackend.business.portfolio.domain.Studio;
import kr.co.yourplanet.ypbackend.business.portfolio.dto.StudioBasicInfo;
import kr.co.yourplanet.ypbackend.business.portfolio.repository.CategoryRepository;
import kr.co.yourplanet.ypbackend.business.portfolio.repository.PortfolioCategoryMapRepository;
import kr.co.yourplanet.ypbackend.business.portfolio.repository.PortfolioLinkRepository;
import kr.co.yourplanet.ypbackend.business.portfolio.repository.StudioRepository;
import kr.co.yourplanet.ypbackend.business.portfolio.service.ProfileService;
import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.repository.MemberRepository;
import kr.co.yourplanet.ypbackend.common.enums.StatusCode;
import kr.co.yourplanet.ypbackend.common.exception.BusinessException;
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

    public StudioBasicInfo getStudio(Long memberId) {
        Optional<Studio> optionalStudio = studioRepository.findByMemberId(memberId);
        if (!optionalStudio.isPresent()) {
            throw new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false);
        }
        Studio studio = optionalStudio.get();
        return StudioBasicInfo.builder()
                .id(studio.getId())
                .name(studio.getToonName())
                .description(studio.getDescription())
                .categories(studio.getCategoryTypes())
                .portfolioLinks(studio.getPortfolioLinkUrls())
                .build();
    }

    public void updateStudio(Long memberId, StudioBasicInfo studioDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 회원 정보가 존재하지 않습니다.", false));
        Studio studio = studioRepository.findByMemberId(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false));
        List<Category> categories = categoryRepository.findAllByCategoryCodeIn(studioDto.getCategories());
        List<PortfolioLink> portfolioLinkList = new ArrayList<>();
        List<PortfolioCategoryMap> portfolioCategoryMapList = new ArrayList<>();

        studio.updateStudio(studioDto.getName(), studioDto.getDescription());
        studio = studioRepository.save(studio);

        for (String link : studioDto.getPortfolioLinks()) {
            portfolioLinkList.add(PortfolioLink.builder()
                    .url(link)
                    .studio(studio)
                    .build());
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

        portfolioLinkRepository.deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(studio, now, now);
        portfolioCategoryMapRepository.deleteAllByStudioAndCreateDateBeforeAndUpdateDateBefore(studio, now, now);

    }

    @Transactional
    public void createStudio(Long memberId, StudioBasicInfo studioDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 회원 정보가 존재하지 않습니다.", false));
        List<Category> categories = categoryRepository.findAllByCategoryCodeIn(studioDto.getCategories());
        List<PortfolioLink> portfolioLinkList = new ArrayList<>();
        List<PortfolioCategoryMap> portfolioCategoryMapList = new ArrayList<>();

        Studio studio = studioRepository.save(Studio.builder()
                .member(member)
                .toonName(studioDto.getName())
                .description(studioDto.getDescription())
                .build());

        for (String link : studioDto.getPortfolioLinks()) {
            portfolioLinkList.add(PortfolioLink.builder()
                    .url(link)
                    .studio(studio)
                    .build());
        }
        portfolioLinkRepository.saveAll(portfolioLinkList);

        for (Category category : categories) {
            portfolioCategoryMapList.add(PortfolioCategoryMap.builder()
                    .category(category)
                    .studio(studio)
                    .build());
        }
        portfolioCategoryMapRepository.saveAll(portfolioCategoryMapList);
    }
}
