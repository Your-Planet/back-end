package kr.co.yourplanet.ypbackend.business.user.controller;

import kr.co.yourplanet.ypbackend.business.user.dto.JoinForm;
import kr.co.yourplanet.ypbackend.business.user.service.MemberService;
import kr.co.yourplanet.ypbackend.common.ResponseForm;
import kr.co.yourplanet.ypbackend.business.user.dto.LoginForm;
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

        memberService.register(joinForm);

        return new ResponseForm<>(StatusCode.OK);
    }

    @PostMapping("/member/login")
    public ResponseForm<String> login(@Valid @RequestBody LoginForm loginForm) {

        String jwtToken = memberService.login(loginForm);

        return new ResponseForm<>(StatusCode.OK, jwtToken);
    }
}