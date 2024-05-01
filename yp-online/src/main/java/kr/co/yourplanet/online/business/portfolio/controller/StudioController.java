package kr.co.yourplanet.online.business.portfolio.controller;

import kr.co.yourplanet.online.business.portfolio.dto.PriceForm;
import kr.co.yourplanet.online.business.portfolio.dto.StudioBasicInfo;
import kr.co.yourplanet.online.business.portfolio.service.PriceService;
import kr.co.yourplanet.online.business.portfolio.service.ProfileService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class StudioController {
    private final ProfileService profileService;
    private final PriceService priceService;
    
    @GetMapping("/studio/profile")
    public ResponseForm<StudioBasicInfo> getStudioProfile(@AuthenticationPrincipal JwtPrincipal principal) {
        StudioBasicInfo studioBasicInfo = profileService.getStudio(principal.getId());
        return new ResponseForm<>(StatusCode.OK, studioBasicInfo);
    }

    @PutMapping("/studio/profile")
    public ResponseForm<Void> updateStudioProfile(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody StudioBasicInfo studioBasicInfo) {
        profileService.updateStudio(principal.getId(), studioBasicInfo);
        return new ResponseForm<>(StatusCode.OK);
    }

    @PostMapping("/studio/profile")
    public ResponseForm<Void> createStudioProfile(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody StudioBasicInfo studioBasicInfo) {
        profileService.createStudio(principal.getId(), studioBasicInfo);
        return new ResponseForm<>(StatusCode.OK);
    }

    @GetMapping("/studio/price")
    public ResponseForm<PriceForm> getPrice(@AuthenticationPrincipal JwtPrincipal principal) {
        PriceForm price = priceService.getPrice(principal.getId());
        return new ResponseForm<>(StatusCode.OK, price);
    }

    @PostMapping("/studio/price")
    public ResponseForm<Void> savePrice(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody @Valid PriceForm priceForm) {
        priceService.savePrice(principal.getId(), priceForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @GetMapping("/studio/price-temp")
    public ResponseForm<PriceForm> getTempPrice(@AuthenticationPrincipal JwtPrincipal principal) {
        PriceForm price = priceService.getTempPrice(principal.getId());
        return new ResponseForm<>(StatusCode.OK, price);
    }

    @PostMapping("/studio/price-temp")
    public ResponseForm<Void> saveTempPrice(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody @Valid PriceForm priceForm) {
        priceService.saveTempPrice(principal.getId(), priceForm);
        return new ResponseForm<>(StatusCode.OK);
    }
}
