package kr.co.yourplanet.online.business.user.controller;

import kr.co.yourplanet.online.business.user.dto.*;
import kr.co.yourplanet.online.business.user.service.MemberService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/join")
    public ResponseForm<Void> join(@Valid @RequestBody JoinForm joinForm) {

        memberService.join(joinForm);

        return new ResponseForm<>(StatusCode.OK);
    }

    @PostMapping("/member/login")
    public ResponseForm<RefreshAccessTokenForm> login(@Valid @RequestBody LoginForm loginForm) {

        return new ResponseForm<>(StatusCode.OK, memberService.login(loginForm));
    }

    @PostMapping("/member/find-email")
    public ResponseForm<String> findId(@Valid @RequestBody FindIdForm accountRecoveryFrom) {
        return new ResponseForm<>(StatusCode.OK, memberService.findId(accountRecoveryFrom));
    }

    @PostMapping("/member/reset-password")
    public ResponseForm<Void> resetPassword(@Valid @RequestBody ResetPasswordForm resetPasswordForm) {
        memberService.resetPassword(resetPasswordForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @PostMapping("/member/validation")
    public ResponseForm<Void> postMethodName(@Valid @RequestBody MemberValidateForm memberValidateForm) {
        memberService.validateMember(memberValidateForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @GetMapping("/member/detail")
    public ResponseForm<MemberDetail> getMemberDetailInfo(@AuthenticationPrincipal JwtPrincipal principal) {
        return new ResponseForm<>(StatusCode.OK, memberService.getMemberDetailInfo(principal.getId()));
    }

    @PostMapping("/member/refresh-token")
    public ResponseForm<RefreshAccessTokenForm> refreshAccessToken(@RequestBody RefreshAccessTokenForm refreshAccessTokenForm) {
        return new ResponseForm<>(StatusCode.OK, memberService.refreshAccessToken(refreshAccessTokenForm));
    }
}