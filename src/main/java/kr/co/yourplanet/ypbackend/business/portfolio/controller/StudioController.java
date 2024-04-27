package kr.co.yourplanet.ypbackend.business.portfolio.controller;

import kr.co.yourplanet.ypbackend.business.portfolio.dto.StudioBasicInfo;
import kr.co.yourplanet.ypbackend.business.portfolio.service.ProfileService;
import kr.co.yourplanet.ypbackend.common.ResponseForm;
import kr.co.yourplanet.ypbackend.common.enums.StatusCode;
import kr.co.yourplanet.ypbackend.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class StudioController {
    private final ProfileService profileService;
    
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
}
