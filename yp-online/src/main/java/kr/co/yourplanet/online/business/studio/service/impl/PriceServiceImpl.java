package kr.co.yourplanet.online.business.studio.service.impl;

import kr.co.yourplanet.core.entity.studio.Price;
import kr.co.yourplanet.core.entity.studio.Studio;
import kr.co.yourplanet.core.entity.studio.TempPrice;
import kr.co.yourplanet.online.business.studio.dto.*;
import kr.co.yourplanet.online.business.studio.repository.PriceRepository;
import kr.co.yourplanet.online.business.studio.repository.StudioRepository;
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
    private final StudioRepository studioRepository;
    public PriceForm getPrice(Long memberId) {
        Price price = priceRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격 정보가 존재하지 않습니다.", false));
        return convertPriceToPriceForm(price);
    }

    @Transactional
    public void savePrice(Long memberId, PriceForm priceForm) {
        Optional<Price> optionalPrice = priceRepository.findById(memberId);
        Price price;
        if (optionalPrice.isPresent()){
            price = optionalPrice.get();
            price.updateDefaultOption(
                    priceForm.getService().getPrice(),
                    priceForm.getService().getWorkingDays(),
                    priceForm.getService().getModificationCount(),
                    priceForm.getService().getDefaultCuts(),
                    priceForm.getService().getPostDurationMonthType()
            );
            price.updateAdditionalCutOption(
                    priceForm.getOption().getAdditionalPanel().getPrice(),
                    priceForm.getOption().getAdditionalPanel().getWorkingDays(),
                    priceForm.getOption().getAdditionalPanel().getProvisionType()
            );
            price.updateAdditionalModificationOption(
                    priceForm.getOption().getAdditionalModification().getPrice(),
                    priceForm.getOption().getAdditionalModification().getWorkingDays(),
                    priceForm.getOption().getAdditionalModification().getProvisionType()
            );
            price.updateRefinementOption(
                    priceForm.getOption().getRefinement().getPrice(),
                    priceForm.getOption().getRefinement().getProvisionType()
            );
            price.updateOriginFileOption(
                    priceForm.getOption().getOriginFile().getPrice(),
                    priceForm.getOption().getOriginFile().getProvisionType()
            );
            price.updatePostDurationExtensionOption(
                    priceForm.getOption().getPostDurationExtension().getPrice(),
                    priceForm.getOption().getPostDurationExtension().getProvisionType()
            );
        } else {
            price = convertPriceFormToPrice(memberId, priceForm);
        }
        priceRepository.save(price);

        // 임시 가격 존재할 경우에만 삭제
        tempPriceRepository.findById(memberId).ifPresent(tempPriceRepository::delete);
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
            tempPrice.updateDefaultOption(
                    priceForm.getService().getPrice(),
                    priceForm.getService().getWorkingDays(),
                    priceForm.getService().getModificationCount(),
                    priceForm.getService().getDefaultCuts(),
                    priceForm.getService().getPostDurationMonthType()
            );
            tempPrice.updateAdditionalCutOption(
                    priceForm.getOption().getAdditionalPanel().getPrice(),
                    priceForm.getOption().getAdditionalPanel().getWorkingDays(),
                    priceForm.getOption().getAdditionalPanel().getProvisionType()
            );
            tempPrice.updateAdditionalModificationOption(
                    priceForm.getOption().getAdditionalModification().getPrice(),
                    priceForm.getOption().getAdditionalModification().getWorkingDays(),
                    priceForm.getOption().getAdditionalModification().getProvisionType()
            );
            tempPrice.updateRefinementOption(
                    priceForm.getOption().getRefinement().getPrice(),
                    priceForm.getOption().getRefinement().getProvisionType()
            );
            tempPrice.updateOriginFileOption(
                    priceForm.getOption().getOriginFile().getPrice(),
                    priceForm.getOption().getOriginFile().getProvisionType()
            );
            tempPrice.updatePostDurationExtensionOption(
                    priceForm.getOption().getPostDurationExtension().getPrice(),
                    priceForm.getOption().getPostDurationExtension().getProvisionType()
            );
        } else {
            tempPrice = convertPriceFormToTempPrice(memberId, priceForm);
        }
        tempPriceRepository.save(tempPrice);
    }

    private PriceForm convertPriceToPriceForm(Price price) {
        PriceForm.DefaultOption defaultOption = PriceForm.DefaultOption.builder()
                .price(price.getPrice())
                .workingDays(price.getWorkingDays())
                .defaultCuts(price.getCuts())
                .modificationCount(price.getModificationCount())
                .postDurationMonthType(price.getPostDurationType())
                .build();

        PriceForm.AdditionalModification additionalModification = PriceForm.AdditionalModification.builder()
                .provisionType(price.getAdditionalModificationOptionType())
                .workingDays(price.getModificationOptionWorkingDays())
                .price(price.getModificationOptionPrice())
                .build();

        PriceForm.AdditionalPanel additionalPanel = PriceForm.AdditionalPanel.builder()
                .provisionType(price.getAdditionalCutOptionType())
                .workingDays(price.getCutOptionWorkingDays())
                .price(price.getCutOptionPrice())
                .build();

        PriceForm.Refinement refineMent = PriceForm.Refinement.builder()
                .provisionType(price.getAdditionalRefinementOptionType())
                .price(price.getRefinementPrice())
                .build();

        PriceForm.OriginFile originFile = PriceForm.OriginFile.builder()
                .provisionType(price.getAdditionalOriginFileOptionType())
                .price(price.getOriginFileOptionPrice())
                .build();

        PriceForm.PostDurationExtension postDurationExtension = PriceForm.PostDurationExtension.builder()
                .provisionType(price.getAdditionalPostDurationExtensionType())
                .price(price.getPostDurationExtensionPrice())
                .build();

        PriceForm.AdditionalPriceForm additionalOption = PriceForm.AdditionalPriceForm.builder()
                .additionalModification(additionalModification)
                .additionalPanel(additionalPanel)
                .refinement(refineMent)
                .originFile(originFile)
                .postDurationExtension(postDurationExtension)
                .build();
        return PriceForm.builder()
                .service(defaultOption)
                .option(additionalOption)
                .build();
    }

    private Price convertPriceFormToPrice(Long memberId, PriceForm priceForm) {
        Studio studio = studioRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격을 저장할 스튜디오가 존재하지 않습니다.", false));
        return Price.builder()
                .studio(studio)
                .price(priceForm.getService().getPrice())
                .workingDays(priceForm.getService().getWorkingDays())
                .cuts(priceForm.getService().getDefaultCuts())
                .modificationCount(priceForm.getService().getModificationCount())
                .postDurationType(priceForm.getService().getPostDurationMonthType())
                .additionalModificationOptionType(priceForm.getOption().getAdditionalModification().getProvisionType())
                .modificationOptionWorkingDays(priceForm.getOption().getAdditionalModification().getWorkingDays())
                .modificationOptionPrice(priceForm.getOption().getAdditionalModification().getPrice())
                .additionalCutOptionType(priceForm.getOption().getAdditionalPanel().getProvisionType())
                .cutOptionWorkingDays(priceForm.getOption().getAdditionalPanel().getWorkingDays())
                .cutOptionPrice(priceForm.getOption().getAdditionalPanel().getPrice())
                .additionalRefinementOptionType(priceForm.getOption().getRefinement().getProvisionType())
                .refinementPrice(priceForm.getOption().getRefinement().getPrice())
                .additionalOriginFileOptionType(priceForm.getOption().getOriginFile().getProvisionType())
                .originFileOptionPrice(priceForm.getOption().getOriginFile().getPrice())
                .additionalPostDurationExtensionType(priceForm.getOption().getPostDurationExtension().getProvisionType())
                .postDurationExtensionPrice(priceForm.getOption().getPostDurationExtension().getPrice())
                .build();
    }

    private PriceForm convertTempPriceToPriceForm(TempPrice tempPrice) {
        PriceForm.DefaultOption defaultOption = PriceForm.DefaultOption.builder()
                .price(tempPrice.getPrice())
                .workingDays(tempPrice.getWorkingDays())
                .defaultCuts(tempPrice.getCuts())
                .modificationCount(tempPrice.getModificationCount())
                .postDurationMonthType(tempPrice.getPostDurationType())
                .build();

        PriceForm.AdditionalModification additionalModification = PriceForm.AdditionalModification.builder()
                .provisionType(tempPrice.getAdditionalModificationOptionType())
                .workingDays(tempPrice.getModificationOptionWorkingDays())
                .price(tempPrice.getModificationOptionPrice())
                .build();

        PriceForm.AdditionalPanel additionalPanel = PriceForm.AdditionalPanel.builder()
                .provisionType(tempPrice.getAdditionalCutOptionType())
                .workingDays(tempPrice.getCutOptionWorkingDays())
                .price(tempPrice.getCutOptionPrice())
                .build();

        PriceForm.Refinement refineMent = PriceForm.Refinement.builder()
                .provisionType(tempPrice.getAdditionalRefinementOptionType())
                .price(tempPrice.getRefinementPrice())
                .build();

        PriceForm.OriginFile originFile = PriceForm.OriginFile.builder()
                .provisionType(tempPrice.getAdditionalOriginFileOptionType())
                .price(tempPrice.getOriginFileOptionPrice())
                .build();

        PriceForm.PostDurationExtension postDurationExtension = PriceForm.PostDurationExtension.builder()
                .provisionType(tempPrice.getAdditionalPostDurationExtensionType())
                .price(tempPrice.getPostDurationExtensionPrice())
                .build();

        PriceForm.AdditionalPriceForm additionalOption = PriceForm.AdditionalPriceForm.builder()
                .additionalModification(additionalModification)
                .additionalPanel(additionalPanel)
                .refinement(refineMent)
                .originFile(originFile)
                .postDurationExtension(postDurationExtension)
                .build();
        return PriceForm.builder()
                .service(defaultOption)
                .option(additionalOption)
                .build();
    }

    private TempPrice convertPriceFormToTempPrice(Long memberId, PriceForm priceForm) {
        Studio studio = studioRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "가격을 저장할 스튜디오가 존재하지 않습니다.", false));
        return TempPrice.builder()
                .studio(studio)
                .price(priceForm.getService().getPrice())
                .workingDays(priceForm.getService().getWorkingDays())
                .cuts(priceForm.getService().getDefaultCuts())
                .modificationCount(priceForm.getService().getModificationCount())
                .postDurationType(priceForm.getService().getPostDurationMonthType())
                .additionalModificationOptionType(priceForm.getOption().getAdditionalModification().getProvisionType())
                .modificationOptionWorkingDays(priceForm.getOption().getAdditionalModification().getWorkingDays())
                .modificationOptionPrice(priceForm.getOption().getAdditionalModification().getPrice())
                .additionalCutOptionType(priceForm.getOption().getAdditionalPanel().getProvisionType())
                .cutOptionWorkingDays(priceForm.getOption().getAdditionalPanel().getWorkingDays())
                .cutOptionPrice(priceForm.getOption().getAdditionalPanel().getPrice())
                .additionalRefinementOptionType(priceForm.getOption().getRefinement().getProvisionType())
                .refinementPrice(priceForm.getOption().getRefinement().getPrice())
                .additionalOriginFileOptionType(priceForm.getOption().getOriginFile().getProvisionType())
                .originFileOptionPrice(priceForm.getOption().getOriginFile().getPrice())
                .additionalPostDurationExtensionType(priceForm.getOption().getPostDurationExtension().getProvisionType())
                .postDurationExtensionPrice(priceForm.getOption().getPostDurationExtension().getPrice())
                .build();
    }
}
