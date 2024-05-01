package kr.co.yourplanet.online.business.portfolio.service.impl;

import kr.co.yourplanet.online.business.portfolio.domain.Price;
import kr.co.yourplanet.core.entity.portfolio.Studio;
import kr.co.yourplanet.online.business.portfolio.domain.TempPrice;
import kr.co.yourplanet.online.business.portfolio.dto.*;
import kr.co.yourplanet.online.business.portfolio.repository.PriceRepository;
import kr.co.yourplanet.online.business.portfolio.repository.StudioRepository;
import kr.co.yourplanet.online.business.portfolio.repository.TempPriceRepository;
import kr.co.yourplanet.online.business.portfolio.service.PriceService;
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
    private final StudioRepository studioRepository;
    public PriceForm getPrice(Long memberId) {
        Price price = priceRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격 정보가 존재하지 않습니다.", false));
        return convertPriceToPriceForm(price);
    }

    @Transactional
    public void savePrice(Long memberId, PriceForm priceForm) {
        Optional<Price> OptionalPrice = priceRepository.findById(memberId);
        Price price;
        if (OptionalPrice.isPresent()){
            price = OptionalPrice.get();
            price.updatePriceInfo(priceForm);
        } else {
            price = convertPriceFormToPrice(memberId, priceForm);
        }
        priceRepository.save(price);
        tempPriceRepository.deleteById(memberId);
    }

    public PriceForm getTempPrice(Long memberId) {
        TempPrice tempPrice = tempPriceRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "임시 가격 정보가 존재하지 않습니다.", false));
        return convertTempPriceToPriceForm(tempPrice);
    }

    @Transactional
    public void saveTempPrice(Long memberId, PriceForm priceForm) {
        Optional<TempPrice> optionalTempPrice = tempPriceRepository.findById(memberId);
        TempPrice tempPrice;

        if (optionalTempPrice.isPresent()){
            tempPrice = optionalTempPrice.get();
            tempPrice.updatePriceInfo(priceForm);
        } else {
            tempPrice = convertPriceFormToTempPrice(memberId, priceForm);
        }
        tempPriceRepository.save(tempPrice);
    }

    private PriceForm convertPriceToPriceForm(Price price) {
        DefaultOption defaultOption = DefaultOption.builder()
                .price(price.getPrice())
                .workingDays(price.getWorkingDays())
                .defaultCuts(price.getCuts())
                .modificationCount(price.getModificationCount())
                .postDurationType(price.getPostDurationType())
                .build();

        AdditionalModification additionalModification = AdditionalModification.builder()
                .provisionType(price.getAdditionalModificationOptionType())
                .workingDays(price.getModificationOptionWorkingDays())
                .price(price.getModificationOptionPrice())
                .build();

        AdditionalPanel additionalPanel = AdditionalPanel.builder()
                .provisionType(price.getAdditionalCutOptionType())
                .workingDays(price.getCutOptionWorkingDays())
                .price(price.getCutOptionPrice())
                .build();

        Refinement refineMent = Refinement.builder()
                .provisionType(price.getAdditionalRefinementOptionType())
                .price(price.getRefinementPrice())
                .build();

        OriginFile originFile = OriginFile.builder()
                .provisionType(price.getAdditionalOriginFileOptionType())
                .price(price.getOriginFileOptionPrice())
                .build();

        PostDurationExtension postDurationExtension = PostDurationExtension.builder()
                .provisionType(price.getAdditionalPostDurationExtensionType())
                .price(price.getPostDurationExtensionPrice())
                .build();

        AdditionalPriceForm additionalOption = AdditionalPriceForm.builder()
                .additionalModification(additionalModification)
                .additionalPanel(additionalPanel)
                .refinement(refineMent)
                .originFile(originFile)
                .postDurationExtension(postDurationExtension)
                .build();
        return PriceForm.builder()
                .defaultOption(defaultOption)
                .additionalOption(additionalOption)
                .build();
    }

    private Price convertPriceFormToPrice(Long memberId, PriceForm priceForm) {
        Studio studio = studioRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격을 저장할 스튜디오가 존재하지 않습니다.", false));
        return Price.builder()
                .studio(studio)
                .price(priceForm.getDefaultOption().getPrice())
                .workingDays(priceForm.getDefaultOption().getWorkingDays())
                .cuts(priceForm.getDefaultOption().getDefaultCuts())
                .modificationCount(priceForm.getDefaultOption().getModificationCount())
                .postDurationType(priceForm.getDefaultOption().getPostDurationType())
                .additionalModificationOptionType(priceForm.getAdditionalOption().getAdditionalModification().getProvisionType())
                .modificationOptionWorkingDays(priceForm.getAdditionalOption().getAdditionalModification().getWorkingDays())
                .modificationOptionPrice(priceForm.getAdditionalOption().getAdditionalModification().getPrice())
                .additionalCutOptionType(priceForm.getAdditionalOption().getAdditionalPanel().getProvisionType())
                .cutOptionWorkingDays(priceForm.getAdditionalOption().getAdditionalPanel().getWorkingDays())
                .cutOptionPrice(priceForm.getAdditionalOption().getAdditionalPanel().getPrice())
                .additionalRefinementOptionType(priceForm.getAdditionalOption().getRefinement().getProvisionType())
                .refinementPrice(priceForm.getAdditionalOption().getRefinement().getPrice())
                .additionalOriginFileOptionType(priceForm.getAdditionalOption().getOriginFile().getProvisionType())
                .originFileOptionPrice(priceForm.getAdditionalOption().getOriginFile().getPrice())
                .additionalPostDurationExtensionType(priceForm.getAdditionalOption().getPostDurationExtension().getProvisionType())
                .postDurationExtensionPrice(priceForm.getAdditionalOption().getPostDurationExtension().getPrice())
                .build();
    }

    private PriceForm convertTempPriceToPriceForm(TempPrice tempPrice) {
        DefaultOption defaultOption = DefaultOption.builder()
                .price(tempPrice.getPrice())
                .workingDays(tempPrice.getWorkingDays())
                .defaultCuts(tempPrice.getCuts())
                .modificationCount(tempPrice.getModificationCount())
                .postDurationType(tempPrice.getPostDurationType())
                .build();

        AdditionalModification additionalModification = AdditionalModification.builder()
                .provisionType(tempPrice.getAdditionalModificationOptionType())
                .workingDays(tempPrice.getModificationOptionWorkingDays())
                .price(tempPrice.getModificationOptionPrice())
                .build();

        AdditionalPanel additionalPanel = AdditionalPanel.builder()
                .provisionType(tempPrice.getAdditionalCutOptionType())
                .workingDays(tempPrice.getCutOptionWorkingDays())
                .price(tempPrice.getCutOptionPrice())
                .build();

        Refinement refineMent = Refinement.builder()
                .provisionType(tempPrice.getAdditionalRefinementOptionType())
                .price(tempPrice.getRefinementPrice())
                .build();

        OriginFile originFile = OriginFile.builder()
                .provisionType(tempPrice.getAdditionalOriginFileOptionType())
                .price(tempPrice.getOriginFileOptionPrice())
                .build();

        PostDurationExtension postDurationExtension = PostDurationExtension.builder()
                .provisionType(tempPrice.getAdditionalPostDurationExtensionType())
                .price(tempPrice.getPostDurationExtensionPrice())
                .build();

        AdditionalPriceForm additionalOption = AdditionalPriceForm.builder()
                .additionalModification(additionalModification)
                .additionalPanel(additionalPanel)
                .refinement(refineMent)
                .originFile(originFile)
                .postDurationExtension(postDurationExtension)
                .build();
        return PriceForm.builder()
                .defaultOption(defaultOption)
                .additionalOption(additionalOption)
                .build();
    }

    private TempPrice convertPriceFormToTempPrice(Long memberId, PriceForm priceForm) {
        Studio studio = studioRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격을 저장할 스튜디오가 존재하지 않습니다.", false));
        return TempPrice.builder()
                .studio(studio)
                .price(priceForm.getDefaultOption().getPrice())
                .workingDays(priceForm.getDefaultOption().getWorkingDays())
                .cuts(priceForm.getDefaultOption().getDefaultCuts())
                .modificationCount(priceForm.getDefaultOption().getModificationCount())
                .postDurationType(priceForm.getDefaultOption().getPostDurationType())
                .additionalModificationOptionType(priceForm.getAdditionalOption().getAdditionalModification().getProvisionType())
                .modificationOptionWorkingDays(priceForm.getAdditionalOption().getAdditionalModification().getWorkingDays())
                .modificationOptionPrice(priceForm.getAdditionalOption().getAdditionalModification().getPrice())
                .additionalCutOptionType(priceForm.getAdditionalOption().getAdditionalPanel().getProvisionType())
                .cutOptionWorkingDays(priceForm.getAdditionalOption().getAdditionalPanel().getWorkingDays())
                .cutOptionPrice(priceForm.getAdditionalOption().getAdditionalPanel().getPrice())
                .additionalRefinementOptionType(priceForm.getAdditionalOption().getRefinement().getProvisionType())
                .refinementPrice(priceForm.getAdditionalOption().getRefinement().getPrice())
                .additionalOriginFileOptionType(priceForm.getAdditionalOption().getOriginFile().getProvisionType())
                .originFileOptionPrice(priceForm.getAdditionalOption().getOriginFile().getPrice())
                .additionalPostDurationExtensionType(priceForm.getAdditionalOption().getPostDurationExtension().getProvisionType())
                .postDurationExtensionPrice(priceForm.getAdditionalOption().getPostDurationExtension().getPrice())
                .build();
    }
}
