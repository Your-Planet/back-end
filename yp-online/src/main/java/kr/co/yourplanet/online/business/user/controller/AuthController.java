package kr.co.yourplanet.online.business.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.user.dto.*;
import kr.co.yourplanet.online.business.user.service.MemberJoinService;
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.business.user.service.MemberService;
import kr.co.yourplanet.online.business.user.service.MemberValidationService;
import kr.co.yourplanet.online.common.ResponseForm;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;
    private final MemberValidationService memberValidationService;
    private final MemberJoinService memberJoinService;

    @PostMapping("/auth/join")
    public ResponseForm<Void> join(
            @Valid @RequestBody MemberJoinForm memberJoinForm
    ) {
        memberJoinService.join(memberJoinForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @PostMapping("/auth/login")
    public ResponseForm<String> login(
            @Valid @RequestBody LoginForm loginForm,
            HttpServletResponse response
    ) {
        RefreshTokenForm refreshTokenForm = memberService.login(loginForm);
        response.addCookie(getRefreshTokenCookie(refreshTokenForm.getRefreshToken()));  // Refresh Token 쿠키 설정

        return new ResponseForm<>(StatusCode.OK, refreshTokenForm.getAccessToken());
    }

    @PostMapping("/auth/find-email")
    public ResponseForm<String> findId(
            @Valid @RequestBody FindIdForm accountRecoveryFrom
    ) {
        return new ResponseForm<>(StatusCode.OK, memberQueryService.findId(accountRecoveryFrom));
    }

    @PostMapping("/auth/validation")
    public ResponseForm<Void> validateMember(
            @Valid @RequestBody MemberValidateForm memberValidateForm
    ) {
        memberValidationService.validateMember(memberValidateForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @PostMapping("/auth/reset-password")
    public ResponseForm<Void> resetPassword(
            @Valid @RequestBody ResetPasswordForm resetPasswordForm
    ) {
        memberService.resetPassword(resetPasswordForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<ResponseForm<String>> refreshAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // 기존 Refresh Token 가져오기
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (!StringUtils.hasText(refreshToken)) {
            return new ResponseEntity<>(
                    new ResponseForm<>(StatusCode.UNAUTHORIZED, "재로그인이 필요합니다.", false),
                    HttpStatus.UNAUTHORIZED);
        }

        // 신규 Refresh, Access Token 발급
        RefreshTokenForm refreshTokenForm = memberService.refreshAccessToken(refreshToken);

        if (!StringUtils.hasText(refreshTokenForm.getRefreshToken())) {
            return new ResponseEntity<>(
                    new ResponseForm<>(StatusCode.UNAUTHORIZED, "재로그인이 필요합니다.", false),
                    HttpStatus.UNAUTHORIZED);
        }

        response.addCookie(getRefreshTokenCookie(refreshTokenForm.getRefreshToken()));

        return new ResponseEntity<>(
                new ResponseForm<>(StatusCode.OK, "토큰 발급에 성공하였습니다.", refreshTokenForm.getAccessToken(), false),
                HttpStatus.OK);
    }

    private Cookie getRefreshTokenCookie(String refreshToken) {
        // Refresh Token 쿠키 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true); // 자바스크립트에서 접근 불가
        //refreshTokenCookie.setSecure(false);   // HTTPS에서만 전송. ssl 설정 완료 후 주석해제 필요
        refreshTokenCookie.setPath("/");      // 전체 도메인에서 접근 가능
        refreshTokenCookie.setMaxAge(60 * 60 * 24); // 쿠키 만료 시간 (1일)

        return refreshTokenCookie;
    }
}
