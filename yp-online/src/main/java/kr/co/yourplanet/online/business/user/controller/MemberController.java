package kr.co.yourplanet.online.business.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.user.dto.MemberDetail;
import kr.co.yourplanet.online.business.user.dto.MemberFullInfo;
import kr.co.yourplanet.online.business.user.dto.update.MemberUpdateForm;
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.business.user.service.MemberUpdateService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "멤버 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberUpdateService memberUpdateService;

    @GetMapping("/member/detail")
    public ResponseForm<MemberDetail> getMemberDetailInfo(@AuthenticationPrincipal JwtPrincipal principal) {
        return new ResponseForm<>(StatusCode.OK, memberQueryService.getSummaryInfo(principal.getId()));
    }

    @Operation(summary = "내 정보 조회 API")
    @GetMapping("/members/me")
    public ResponseForm<MemberFullInfo> getMyInfo(
            @AuthenticationPrincipal JwtPrincipal principal
    ) {
        MemberFullInfo response = memberQueryService.getFullInfo(principal.getId());
        return new ResponseForm<>(StatusCode.OK, response);
    }

    @Operation(summary = "내 정보 수정 API")
    @PatchMapping("/members/me")
    public ResponseForm<Void> updateMyInfo(
            @AuthenticationPrincipal JwtPrincipal principal,
            @Valid @RequestBody MemberUpdateForm memberUpdateForm
    ) {
        memberUpdateService.updateMember(principal.getId(), memberUpdateForm);
        return new ResponseForm<>(StatusCode.OK, "회원 정보 수정이 완료 되었습니다.", true);
    }
}