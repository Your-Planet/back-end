package kr.co.yourplanet.online.business.studio.controller;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.studio.dto.*;
import kr.co.yourplanet.online.business.studio.service.PriceService;
import kr.co.yourplanet.online.business.studio.service.ProfileService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudioController {
    private final ProfileService profileService;
    private final PriceService priceService;

    @GetMapping("/studio/profile")
    public ResponseForm<ProfileInfo> getStudioProfile(@AuthenticationPrincipal JwtPrincipal principal) {
        ProfileInfo profileInfo = profileService.getStudio(principal.getId());
        return new ResponseForm<>(StatusCode.OK, profileInfo);
    }

    @PostMapping(value = "/studio/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseForm<Void> createStudioProfile(@AuthenticationPrincipal JwtPrincipal principal, @RequestPart StudioRegisterForm studioRegisterForm
            , @RequestPart(required = false) MultipartFile profileImage) {
        profileService.upsertAndDeleteStudio(principal.getId(), studioRegisterForm, profileImage);
        return new ResponseForm<>(StatusCode.OK);
    }

    @GetMapping("/studio/price")
    public ResponseForm<PriceInfo> getPrice(@AuthenticationPrincipal JwtPrincipal principal) {
        PriceInfo price = priceService.getPrice(principal.getId());
        return new ResponseForm<>(StatusCode.OK, price);
    }

    @PostMapping("/studio/price")
    public ResponseForm<Void> savePrice(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody @Valid PriceInfo priceInfo) {
        priceService.savePrice(principal.getId(), priceInfo);
        return new ResponseForm<>(StatusCode.OK);
    }

    @GetMapping("/studio/price-temp")
    public ResponseForm<PriceInfo> getTempPrice(@AuthenticationPrincipal JwtPrincipal principal) {
        PriceInfo price = priceService.getTempPrice(principal.getId());
        return new ResponseForm<>(StatusCode.OK, price);
    }

    @PostMapping("/studio/price-temp")
    public ResponseForm<Void> saveTempPrice(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody @Valid PriceInfo priceInfo) {
        priceService.saveTempPrice(principal.getId(), priceInfo);
        return new ResponseForm<>(StatusCode.OK);
    }

    @GetMapping("/studio")
    public ResponseForm<Page<StudioBasicInfo>> searchStudios(@RequestParam(name = "categories", required = false) List<String> categories,
                                                             @RequestParam(name = "keywordType", required = false) String keywordType,
                                                             @RequestParam(name = "keyword", required = false) String keyword,
                                                             @RequestParam(name = "minPrice", required = false) Integer minPrice,
                                                             @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
                                                             @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                                             @RequestParam(name = "pageSize", required = false) Integer pageSize, @AuthenticationPrincipal JwtPrincipal principal) {
        Page<StudioBasicInfo> searchResults = profileService.searchStudios(categories, keywordType, keyword, minPrice, maxPrice, pageNumber, pageSize);
        return new ResponseForm<>(StatusCode.OK, searchResults);

    }

    @GetMapping("/studio/{id}")
    public ResponseForm<StudioDetailInfo> getCreatorStudioInfo(@PathVariable(name = "id") Long studioId) {

        StudioDetailInfo studioDetailInfo = new StudioDetailInfo();
        return new ResponseForm<>(StatusCode.OK, studioDetailInfo);
    }
}
