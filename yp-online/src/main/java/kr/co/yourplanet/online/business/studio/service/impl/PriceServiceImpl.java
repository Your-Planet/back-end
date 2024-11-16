package kr.co.yourplanet.online.business.studio.service.impl;

import kr.co.yourplanet.core.entity.studio.Price;
import kr.co.yourplanet.core.entity.studio.Profile;
import kr.co.yourplanet.core.entity.studio.TempPrice;
import kr.co.yourplanet.online.business.studio.dto.*;
import kr.co.yourplanet.online.business.studio.repository.PriceRepository;
import kr.co.yourplanet.online.business.studio.repository.ProfileRepository;
import kr.co.yourplanet.online.business.studio.repository.TempPriceRepository;
import kr.co.yourplanet.online.business.studio.service.PriceService;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final TempPriceRepository tempPriceRepository;
    private final ProfileRepository profileRepository;

    public PriceInfo getPriceInfoByMemberId(Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false));

        Price price = profile.getLatestPrice().orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격 정보가 존재하지 않습니다.", false));
        return convertPriceToPriceForm(price);
    }

    // 가격정보 없는 Price (기본제공 옵셥 등만 존재)
    public PriceInfoWithoutPrice getPriceInfoWithoutPriceByMemberId(Long creatorId) {
        Profile profile = profileRepository.findByMemberId(creatorId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false));

        Price price = profile.getLatestPrice().orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격 정보가 존재하지 않습니다.", false));

        return PriceInfoWithoutPrice.from(convertPriceToPriceForm(price), price.getId());
    }

    @Transactional

    public void savePrice(Long memberId, PriceInfo priceInfo) {
        Profile profile = profileRepository.findByMemberId(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false));

        // 이전 Price 정보에 최신여부 N 업데이트
        Optional<Price> latestPrice = profile.getLatestPrice();
        latestPrice.ifPresent(Price::markAsNotLatest);


        Price price = convertPriceFormToPrice(memberId, priceInfo);
        priceRepository.save(price);

        // 임시 가격 존재할 경우에만 삭제
        tempPriceRepository.findByMemberId(memberId).ifPresent(tempPriceRepository::delete);
    }

    public PriceInfo getTempPrice(Long memberId) {
        TempPrice tempPrice = tempPriceRepository.findByMemberId(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "임시 가격 정보가 존재하지 않습니다.", false));
        return convertTempPriceToPriceForm(tempPrice);
    }

    @Transactional
    public void saveTempPrice(Long memberId, PriceInfo priceInfo) {
        Optional<TempPrice> optionalTempPrice = tempPriceRepository.findByMemberId(memberId);
        TempPrice tempPrice;

        if (optionalTempPrice.isPresent()) {
            tempPrice = optionalTempPrice.get();
            tempPrice.updateDefaultOption(
                    priceInfo.getService().getPrice(),
                    priceInfo.getService().getWorkingDays(),
                    priceInfo.getService().getModificationCount(),
                    priceInfo.getService().getDefaultCuts(),
                    priceInfo.getService().getPostDurationMonthType()
            );
            tempPrice.updateAdditionalCutOption(
                    priceInfo.getOption().getAdditionalPanel().getPrice(),
                    priceInfo.getOption().getAdditionalPanel().getWorkingDays(),
                    priceInfo.getOption().getAdditionalPanel().getProvisionType()
            );
            tempPrice.updateAdditionalModificationOption(
                    priceInfo.getOption().getAdditionalModification().getPrice(),
                    priceInfo.getOption().getAdditionalModification().getWorkingDays(),
                    priceInfo.getOption().getAdditionalModification().getProvisionType()
            );
            tempPrice.updateRefinementOption(
                    priceInfo.getOption().getRefinement().getPrice(),
                    priceInfo.getOption().getRefinement().getProvisionType()
            );
            tempPrice.updateOriginFileOption(
                    priceInfo.getOption().getOriginFile().getPrice(),
                    priceInfo.getOption().getOriginFile().getProvisionType()
            );
            tempPrice.updatePostDurationExtensionOption(
                    priceInfo.getOption().getPostDurationExtension().getPrice(),
                    priceInfo.getOption().getPostDurationExtension().getProvisionType()
            );
        } else {
            tempPrice = convertPriceFormToTempPrice(memberId, priceInfo);
        }
        tempPriceRepository.save(tempPrice);
    }

    private PriceInfo convertPriceToPriceForm(Price price) {
        PriceInfo.DefaultOption defaultOption = PriceInfo.DefaultOption.builder()
                .price(price.getPrice())
                .workingDays(price.getWorkingDays())
                .defaultCuts(price.getCuts())
                .modificationCount(price.getModificationCount())
                .postDurationMonthType(price.getPostDurationType())
                .build();

        PriceInfo.AdditionalModification additionalModification = PriceInfo.AdditionalModification.builder()
                .provisionType(price.getAdditionalModificationOptionType())
                .workingDays(price.getModificationOptionWorkingDays())
                .price(price.getModificationOptionPrice())
                .build();

        PriceInfo.AdditionalPanel additionalPanel = PriceInfo.AdditionalPanel.builder()
                .provisionType(price.getAdditionalCutOptionType())
                .workingDays(price.getCutOptionWorkingDays())
                .price(price.getCutOptionPrice())
                .build();

        PriceInfo.Refinement refineMent = PriceInfo.Refinement.builder()
                .provisionType(price.getAdditionalRefinementOptionType())
                .price(price.getRefinementPrice())
                .build();

        PriceInfo.OriginFile originFile = PriceInfo.OriginFile.builder()
                .provisionType(price.getAdditionalOriginFileOptionType())
                .price(price.getOriginFileOptionPrice())
                .build();

        PriceInfo.PostDurationExtension postDurationExtension = PriceInfo.PostDurationExtension.builder()
                .provisionType(price.getAdditionalPostDurationExtensionType())
                .price(price.getPostDurationExtensionPrice())
                .build();

        PriceInfo.AdditionalPriceForm additionalOption = PriceInfo.AdditionalPriceForm.builder()
                .additionalModification(additionalModification)
                .additionalPanel(additionalPanel)
                .refinement(refineMent)
                .originFile(originFile)
                .postDurationExtension(postDurationExtension)
                .build();
        return PriceInfo.builder()
                .service(defaultOption)
                .option(additionalOption)
                .build();
    }

    private Price convertPriceFormToPrice(Long memberId, PriceInfo priceInfo) {
        Profile profile = profileRepository.findByMemberId(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격을 저장할 스튜디오가 존재하지 않습니다.", false));
        return Price.builder()
                .profile(profile)
                .price(priceInfo.getService().getPrice())
                .workingDays(priceInfo.getService().getWorkingDays())
                .cuts(priceInfo.getService().getDefaultCuts())
                .modificationCount(priceInfo.getService().getModificationCount())
                .postDurationType(priceInfo.getService().getPostDurationMonthType())
                .additionalModificationOptionType(priceInfo.getOption().getAdditionalModification().getProvisionType())
                .modificationOptionWorkingDays(priceInfo.getOption().getAdditionalModification().getWorkingDays())
                .modificationOptionPrice(priceInfo.getOption().getAdditionalModification().getPrice())
                .additionalCutOptionType(priceInfo.getOption().getAdditionalPanel().getProvisionType())
                .cutOptionWorkingDays(priceInfo.getOption().getAdditionalPanel().getWorkingDays())
                .cutOptionPrice(priceInfo.getOption().getAdditionalPanel().getPrice())
                .additionalRefinementOptionType(priceInfo.getOption().getRefinement().getProvisionType())
                .refinementPrice(priceInfo.getOption().getRefinement().getPrice())
                .additionalOriginFileOptionType(priceInfo.getOption().getOriginFile().getProvisionType())
                .originFileOptionPrice(priceInfo.getOption().getOriginFile().getPrice())
                .additionalPostDurationExtensionType(priceInfo.getOption().getPostDurationExtension().getProvisionType())
                .postDurationExtensionPrice(priceInfo.getOption().getPostDurationExtension().getPrice())
                .isLatest(true)
                .build();
    }

    private PriceInfo convertTempPriceToPriceForm(TempPrice tempPrice) {
        PriceInfo.DefaultOption defaultOption = PriceInfo.DefaultOption.builder()
                .price(tempPrice.getPrice())
                .workingDays(tempPrice.getWorkingDays())
                .defaultCuts(tempPrice.getCuts())
                .modificationCount(tempPrice.getModificationCount())
                .postDurationMonthType(tempPrice.getPostDurationType())
                .build();

        PriceInfo.AdditionalModification additionalModification = PriceInfo.AdditionalModification.builder()
                .provisionType(tempPrice.getAdditionalModificationOptionType())
                .workingDays(tempPrice.getModificationOptionWorkingDays())
                .price(tempPrice.getModificationOptionPrice())
                .build();

        PriceInfo.AdditionalPanel additionalPanel = PriceInfo.AdditionalPanel.builder()
                .provisionType(tempPrice.getAdditionalCutOptionType())
                .workingDays(tempPrice.getCutOptionWorkingDays())
                .price(tempPrice.getCutOptionPrice())
                .build();

        PriceInfo.Refinement refineMent = PriceInfo.Refinement.builder()
                .provisionType(tempPrice.getAdditionalRefinementOptionType())
                .price(tempPrice.getRefinementPrice())
                .build();

        PriceInfo.OriginFile originFile = PriceInfo.OriginFile.builder()
                .provisionType(tempPrice.getAdditionalOriginFileOptionType())
                .price(tempPrice.getOriginFileOptionPrice())
                .build();

        PriceInfo.PostDurationExtension postDurationExtension = PriceInfo.PostDurationExtension.builder()
                .provisionType(tempPrice.getAdditionalPostDurationExtensionType())
                .price(tempPrice.getPostDurationExtensionPrice())
                .build();

        PriceInfo.AdditionalPriceForm additionalOption = PriceInfo.AdditionalPriceForm.builder()
                .additionalModification(additionalModification)
                .additionalPanel(additionalPanel)
                .refinement(refineMent)
                .originFile(originFile)
                .postDurationExtension(postDurationExtension)
                .build();
        return PriceInfo.builder()
                .service(defaultOption)
                .option(additionalOption)
                .build();
    }

    private TempPrice convertPriceFormToTempPrice(Long memberId, PriceInfo priceInfo) {
        Profile profile = profileRepository.findByMemberId(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격을 저장할 스튜디오가 존재하지 않습니다.", false));
        return TempPrice.builder()
                .member(profile.getMember())
                .price(priceInfo.getService().getPrice())
                .workingDays(priceInfo.getService().getWorkingDays())
                .cuts(priceInfo.getService().getDefaultCuts())
                .modificationCount(priceInfo.getService().getModificationCount())
                .postDurationType(priceInfo.getService().getPostDurationMonthType())
                .additionalModificationOptionType(priceInfo.getOption().getAdditionalModification().getProvisionType())
                .modificationOptionWorkingDays(priceInfo.getOption().getAdditionalModification().getWorkingDays())
                .modificationOptionPrice(priceInfo.getOption().getAdditionalModification().getPrice())
                .additionalCutOptionType(priceInfo.getOption().getAdditionalPanel().getProvisionType())
                .cutOptionWorkingDays(priceInfo.getOption().getAdditionalPanel().getWorkingDays())
                .cutOptionPrice(priceInfo.getOption().getAdditionalPanel().getPrice())
                .additionalRefinementOptionType(priceInfo.getOption().getRefinement().getProvisionType())
                .refinementPrice(priceInfo.getOption().getRefinement().getPrice())
                .additionalOriginFileOptionType(priceInfo.getOption().getOriginFile().getProvisionType())
                .originFileOptionPrice(priceInfo.getOption().getOriginFile().getPrice())
                .additionalPostDurationExtensionType(priceInfo.getOption().getPostDurationExtension().getProvisionType())
                .postDurationExtensionPrice(priceInfo.getOption().getPostDurationExtension().getPrice())
                .build();
    }
}
