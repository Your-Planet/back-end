package kr.co.yourplanet.online.business.instagram.controller;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.instagram.dto.InstagramMediaForm;
import kr.co.yourplanet.online.business.instagram.service.InstagramService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InstagramController {

    private final InstagramService instagramService;

    @GetMapping("/instagram/medias")
    public ResponseForm<List<InstagramMediaForm>> getMedias(@RequestParam(required = false) String permalink, @AuthenticationPrincipal JwtPrincipal principal) {
        List<InstagramMediaForm> instagramMediaList;

        if (StringUtils.hasText(permalink)) {
            instagramMediaList = instagramService.getMediasByPermalink(permalink, principal.getId());
        } else{
            instagramMediaList = instagramService.getAllMediasByMemberId(principal.getId());
        }

        return new ResponseForm<>(StatusCode.OK, instagramMediaList);
    }
}
