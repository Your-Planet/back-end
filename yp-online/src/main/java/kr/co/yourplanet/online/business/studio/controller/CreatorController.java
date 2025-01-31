package kr.co.yourplanet.online.business.studio.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.studio.dto.CreatorBasicInfo;
import kr.co.yourplanet.online.business.studio.dto.CreatorDetailInfo;
import kr.co.yourplanet.online.business.studio.service.CreatorService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Creator", description = "작가 API")
@RestController
@RequiredArgsConstructor
public class CreatorController {

    @Qualifier("CreatorServiceImpl")
    private final CreatorService creatorService;

    @GetMapping("/creator")
    public ResponseForm<Page<CreatorBasicInfo>> searchStudios(@RequestParam(name = "categories", required = false) List<String> categories,
                                                              @RequestParam(name = "keywordType", required = false) String keywordType,
                                                              @RequestParam(name = "keyword", required = false) String keyword,
                                                              @RequestParam(name = "minPrice", required = false) Integer minPrice,
                                                              @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
                                                              @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                                              @RequestParam(name = "pageSize", required = false) Integer pageSize, @AuthenticationPrincipal JwtPrincipal principal) {
        Page<CreatorBasicInfo> searchResults = creatorService.searchStudios(principal.getId(), categories, keywordType, keyword, minPrice, maxPrice, pageNumber, pageSize);
        return new ResponseForm<>(StatusCode.OK, searchResults);

    }

    @GetMapping("/creator/{id}")
    public ResponseForm<CreatorDetailInfo> getCreatorStudioInfo(@PathVariable(name = "id") Long creatorId, @AuthenticationPrincipal JwtPrincipal principal) {

        CreatorDetailInfo creatorDetailInfo = creatorService.getStudioDetail(creatorId, principal.getId());
        return new ResponseForm<>(StatusCode.OK, creatorDetailInfo);
    }
}
