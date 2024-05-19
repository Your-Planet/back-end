package kr.co.yourplanet.online.business.instagram.controller;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.instagram.dto.InstagramMediasForm;
import kr.co.yourplanet.online.business.instagram.service.InstagramService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InstagramController {

    private final InstagramService instagramService;

    @GetMapping("/instagram/medias")
    public ResponseForm<InstagramMediasForm> getMedias(@RequestParam(required = false) String permalink, @AuthenticationPrincipal JwtPrincipal principal) {
        InstagramMediasForm instagramMediasForm;

        if (StringUtils.hasText(permalink)) {
            instagramMediasForm = instagramService.getMediasByPermalink(permalink, principal.getId());
        } else{
            instagramMediasForm = instagramService.getAllMediasByMemberId(principal.getId());
        }

        return new ResponseForm<>(StatusCode.OK, instagramMediasForm);
    }
}
