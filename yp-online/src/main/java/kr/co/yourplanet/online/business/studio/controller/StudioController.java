package kr.co.yourplanet.online.business.studio.controller;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.studio.dto.PriceInfo;
import kr.co.yourplanet.online.business.studio.dto.ProfileInfo;
import kr.co.yourplanet.online.business.studio.dto.ProfileRegisterForm;
import kr.co.yourplanet.online.business.studio.service.PriceService;
import kr.co.yourplanet.online.business.studio.service.ProfileService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
public class StudioController {
    private final ProfileService profileService;
    private final PriceService priceService;

    @GetMapping("/studio/profile")
    public ResponseForm<ProfileInfo> getStudioProfile(@AuthenticationPrincipal JwtPrincipal principal) {
        ProfileInfo profileInfo = profileService.getStudioProfileByMemberId(principal.getId());
        return new ResponseForm<>(StatusCode.OK, profileInfo);
    }

    @PostMapping(value = "/studio/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseForm<Void> createStudioProfile(@RequestPart ProfileRegisterForm profileRegisterForm, @RequestPart(required = false) MultipartFile profileImage, @AuthenticationPrincipal JwtPrincipal principal) {
        profileService.upsertAndDeleteProfile(principal.getId(), profileRegisterForm, profileImage);
        return new ResponseForm<>(StatusCode.OK);
    }

    @GetMapping("/studio/price")
    public ResponseForm<PriceInfo> getPrice(@AuthenticationPrincipal JwtPrincipal principal) {
        PriceInfo price = priceService.getPriceInfoByMemberId(principal.getId());
        return new ResponseForm<>(StatusCode.OK, price);
    }

    @PostMapping("/studio/price")
    public ResponseForm<Void> savePrice(@RequestBody @Valid PriceInfo priceInfo, @AuthenticationPrincipal JwtPrincipal principal) {
        priceService.savePrice(principal.getId(), priceInfo);
        return new ResponseForm<>(StatusCode.OK);
    }

    @GetMapping("/studio/price-temp")
    public ResponseForm<PriceInfo> getTempPrice(@AuthenticationPrincipal JwtPrincipal principal) {
        PriceInfo price = priceService.getTempPrice(principal.getId());
        return new ResponseForm<>(StatusCode.OK, price);
    }

    @PostMapping("/studio/price-temp")
    public ResponseForm<Void> saveTempPrice(@RequestBody @Valid PriceInfo priceInfo, @AuthenticationPrincipal JwtPrincipal principal) {
        priceService.saveTempPrice(principal.getId(), priceInfo);
        return new ResponseForm<>(StatusCode.OK);
    }

}
