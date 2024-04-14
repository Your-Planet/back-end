package kr.co.yourplanet.ypbackend.business.user.controller;

import kr.co.yourplanet.ypbackend.business.user.dto.FindIdForm;
import kr.co.yourplanet.ypbackend.business.user.dto.JoinForm;
import kr.co.yourplanet.ypbackend.business.user.dto.ResetPasswordForm;
import kr.co.yourplanet.ypbackend.business.user.service.MemberService;
import kr.co.yourplanet.ypbackend.common.ResponseForm;
import kr.co.yourplanet.ypbackend.business.user.dto.LoginForm;
import kr.co.yourplanet.ypbackend.business.user.dto.MemberValidateForm;
import kr.co.yourplanet.ypbackend.common.enums.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseForm<String> login(@Valid @RequestBody LoginForm loginForm) {

        String jwtToken = memberService.login(loginForm);

        return new ResponseForm<>(StatusCode.OK, jwtToken);
    }

    @PostMapping("/member/find-id")
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
    
}