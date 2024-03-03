package kr.co.yourplanet.ypbackend.business.user.controller;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.dto.RegisterForm;
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
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/register")
    public ResponseForm<?> register(@Valid @RequestBody RegisterForm registerForm) {
        Member member = Member.builder()
                .email(registerForm.getEmail())
                .password(registerForm.getPassword())
                .name(registerForm.getName())
                .genderType(registerForm.getGenderType())
                .tel(registerForm.getTel())
                .memberType(registerForm.getMemberType())
                .birthDate(registerForm.getBirthDate())
                .instagramId(registerForm.getInstagramId())
                .companyName(registerForm.getCompanyName())
                .businessNumber(registerForm.getBusinessNumber())
                .representativeName(registerForm.getRepresentativeName())
                .businessAddress(registerForm.getBusinessAddress())
                .termsAgreedTimestamp(LocalDateTime.now())
                .build();

        memberService.register(member);

        return new ResponseForm<>(StatusCode.OK);
    }

    @PostMapping("/member/login")
    public ResponseForm<String> login(@Valid @RequestBody LoginForm loginForm) {

        String jwtToken = memberService.login(loginForm);

        return new ResponseForm<>(StatusCode.OK, null, jwtToken);
    }
}