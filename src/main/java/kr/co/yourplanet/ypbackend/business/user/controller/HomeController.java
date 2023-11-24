package kr.co.yourplanet.ypbackend.business.user.controller;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.dto.RegisterForm;
import kr.co.yourplanet.ypbackend.business.user.service.HomeService;
import kr.co.yourplanet.ypbackend.common.ResponseForm;
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
public class HomeController {

    private final HomeService homeService;

    @PostMapping("/register")
    public ResponseEntity<ResponseForm> register(@RequestBody RegisterForm registerForm){
        ResponseForm responseForm = new ResponseForm();

        Member member = Member.builder()
                .id(registerForm.getId())
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
                .build();

        homeService.register(member);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

}
