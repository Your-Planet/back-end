package kr.co.yourplanet.ypbackend.business.user.controller;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.dto.RegisterForm;
import kr.co.yourplanet.ypbackend.business.user.service.MemberService;
import kr.co.yourplanet.ypbackend.common.ResponseForm;
import kr.co.yourplanet.ypbackend.business.user.dto.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<ResponseForm> register(@RequestBody RegisterForm registerForm) {
        ResponseForm responseForm = new ResponseForm();

        Member member = Member.builder()
                .id(registerForm.getId())
                .password(registerForm.getPassword())
                .name(registerForm.getName())
                .sex(registerForm.isSex())
                .tel(registerForm.getTel())
                .memberType(registerForm.getMemberType())
                .build();

        memberService.register(member);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseForm> login(@RequestBody LoginForm loginForm) {
        ResponseForm responseForm = new ResponseForm();

        Member member = homeService.login(loginForm);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }
}
