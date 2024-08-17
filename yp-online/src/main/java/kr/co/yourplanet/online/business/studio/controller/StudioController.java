package kr.co.yourplanet.online.business.studio.controller;

import kr.co.yourplanet.online.business.studio.dto.PriceForm;
import kr.co.yourplanet.online.business.studio.dto.StudioBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.StudioBasicSearch;
import kr.co.yourplanet.online.business.studio.dto.StudioRegisterForm;
import kr.co.yourplanet.online.business.studio.service.PriceService;
import kr.co.yourplanet.online.business.studio.service.ProfileService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.core.enums.StatusCode;
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
    public ResponseForm<StudioBasicInfo> getStudioProfile(@AuthenticationPrincipal JwtPrincipal principal) {
        StudioBasicInfo studioBasicInfo = profileService.getStudio(principal.getId());
        return new ResponseForm<>(StatusCode.OK, studioBasicInfo);
    }

    @PostMapping(value = "/studio/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseForm<Void> createStudioProfile(@AuthenticationPrincipal JwtPrincipal principal, @RequestPart StudioRegisterForm studioRegisterForm
            , @RequestPart(required = false) MultipartFile profileImage) {
        profileService.upsertAndDeleteStudio(principal.getId(), studioRegisterForm, profileImage);
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

    @GetMapping("/studio")
    public ResponseForm<Page<StudioBasicSearch>> searchStudios(@RequestParam(name = "categories", required = false) List<String> categories,
                                                               @RequestParam(name = "keywordType", required = false) String keywordType,
                                                               @RequestParam(name = "keyword", required = false) String keyword,
                                                               @RequestParam(name = "minPrice", required = false) Integer minPrice,
                                                               @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
                                                               @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                                               @RequestParam(name = "pageSize", required = false) Integer pageSize, @AuthenticationPrincipal JwtPrincipal principal) {
        Page<StudioBasicSearch> searchResults = profileService.searchStudios(categories, keywordType, keyword, minPrice, maxPrice, pageNumber, pageSize);
        return new ResponseForm<>(StatusCode.OK, searchResults);

    }
}
