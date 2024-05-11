package kr.co.yourplanet.online.business.studio.controller;

import kr.co.yourplanet.online.business.studio.dto.PriceForm;
import kr.co.yourplanet.online.business.studio.dto.StudioBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioResiterForm;
import kr.co.yourplanet.online.business.studio.service.PriceService;
import kr.co.yourplanet.online.business.studio.service.ProfileService;
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

    @PostMapping("/studio/profile")
    public ResponseForm<Void> createStudioProfile(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody StudioResiterForm studioResiterForm) {
        profileService.upsertAndDeleteStudio(principal.getId(), studioResiterForm);
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
