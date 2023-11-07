package kr.co.yourplanet.ypbackend.business.user.controller;

import kr.co.yourplanet.ypbackend.business.user.dto.RegisterForm;
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

    @PostMapping("/register")
    public ResponseEntity<ResponseForm> register(@RequestBody RegisterForm registerForm){

        ResponseForm responseForm = new ResponseForm();

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

}
