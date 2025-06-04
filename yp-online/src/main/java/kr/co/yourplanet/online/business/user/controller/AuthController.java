package kr.co.yourplanet.online.business.user.controller;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.user.dto.request.ChangePasswordForm;
import kr.co.yourplanet.online.business.user.dto.request.FindIdForm;
import kr.co.yourplanet.online.business.user.dto.request.LoginForm;
import kr.co.yourplanet.online.business.user.dto.request.MemberJoinForm;
import kr.co.yourplanet.online.business.user.dto.request.MemberValidateForm;
import kr.co.yourplanet.online.business.user.dto.request.RefreshTokenForm;
import kr.co.yourplanet.online.business.user.dto.request.ResetPasswordForm;
import kr.co.yourplanet.online.business.user.dto.request.ValidatePasswordForm;
import kr.co.yourplanet.online.business.user.service.MemberJoinService;
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.business.user.service.MemberService;
import kr.co.yourplanet.online.business.user.service.MemberValidationService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;
    private final MemberValidationService memberValidationService;
    private final MemberJoinService memberJoinService;

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    @Operation(summary = "회원 가입")
    @PostMapping("/auth/join")
    public ResponseForm<Void> join(
            @Valid @RequestBody MemberJoinForm memberJoinForm
    ) {
        memberJoinService.join(memberJoinForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @Operation(summary = "로그인")
    @PostMapping("/auth/login")
    public ResponseForm<String> login(
            @Valid @RequestBody LoginForm loginForm,
            HttpServletResponse response
    ) {
        RefreshTokenForm refreshTokenForm = memberService.login(loginForm);
        response.addCookie(generateRefreshTokenCookie(refreshTokenForm.getRefreshToken()));  // Refresh Token 쿠키 설정

        return new ResponseForm<>(StatusCode.OK, refreshTokenForm.getAccessToken());
    }

    @Operation(summary = "이메일 찾기")
    @PostMapping("/auth/find-email")
    public ResponseForm<String> findId(
            @Valid @RequestBody FindIdForm accountRecoveryFrom
    ) {
        return new ResponseForm<>(StatusCode.OK, memberQueryService.findId(accountRecoveryFrom));
    }

    @Operation(summary = "멤버 검증")
    @PostMapping("/auth/validation")
    public ResponseForm<Void> validateMember(
            @Valid @RequestBody MemberValidateForm memberValidateForm
    ) {
        memberValidationService.validateMember(memberValidateForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @Operation(summary = "비밀번호 검증")
    @PostMapping("/auth/validate-password")
    public ResponseForm<Void> validatePassword(
            @AuthenticationPrincipal JwtPrincipal principal,
            @Valid @RequestBody ValidatePasswordForm validatePasswordForm
    ) {
        memberValidationService.validatePassword(principal.getId(), validatePasswordForm.password());
        return new ResponseForm<>(StatusCode.OK);
    }

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/auth/change-password")
    public ResponseForm<Void> changePassword(
            @AuthenticationPrincipal JwtPrincipal principal,
            @Valid @RequestBody ChangePasswordForm changePasswordForm
    ) {
        memberService.changePassword(principal.getId(), changePasswordForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @Operation(summary = "비밀번호 초기화")
    @PostMapping("/auth/reset-password")
    public ResponseForm<Void> resetPassword(
            @Valid @RequestBody ResetPasswordForm resetPasswordForm
    ) {
        memberService.resetPassword(resetPasswordForm);
        return new ResponseForm<>(StatusCode.OK);
    }

    @Operation(summary = "접근 토큰 갱신")
    @PostMapping("/auth/refresh-token")
    public ResponseEntity<ResponseForm<String>> refreshAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String refreshToken = extractRefreshTokenFromCookies(request);

        if (isTokenInvalid(refreshToken)) {
            return unauthorizedResponse("재로그인이 필요합니다.");
        }

        // 신규 Refresh, Access Token 발급
        RefreshTokenForm refreshTokenForm = memberService.refreshAccessToken(refreshToken);

        if (isTokenInvalid(refreshTokenForm.getRefreshToken())) {
            return unauthorizedResponse("재로그인이 필요합니다.");
        }

        response.addCookie(generateRefreshTokenCookie(refreshTokenForm.getRefreshToken()));

        return ResponseEntity.ok(
            new ResponseForm<>(StatusCode.OK, "토큰 발급에 성공하였습니다.", refreshTokenForm.getAccessToken(), false)
        );
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<ResponseForm<Void>> logout(HttpServletRequest request,
        HttpServletResponse response) {

        String refreshToken = extractRefreshTokenFromCookies(request);

        memberService.logout(refreshToken);

        // Refresh Token 쿠키 삭제
        deleteRefreshTokenCookie(response);

        return ResponseEntity.ok(
            new ResponseForm<>(StatusCode.OK, "로그아웃 되었습니다.", false)
        );
    }

    /**
     * 쿠키에서 Refresh Token 추출
     */
    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return "";

        return Arrays.stream(request.getCookies())
            .filter(cookie -> REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
            .map(Cookie::getValue)
            .findFirst()
            .orElse("");
    }

    /**
     * 토큰 유효성 검사
     */
    private boolean isTokenInvalid(String token) {
        return !StringUtils.hasText(token);
    }

    /**
     * UNAUTHORIZED 공통 응답 반환
     */
    private ResponseEntity<ResponseForm<String>> unauthorizedResponse(String message) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ResponseForm<>(StatusCode.UNAUTHORIZED, message, false));
    }

    /**
     * Refresh Token 쿠키 생성
     */
    private Cookie generateRefreshTokenCookie(String refreshToken) {
        // Refresh Token 쿠키 설정
        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        refreshTokenCookie.setHttpOnly(true); // 자바스크립트에서 접근 불가
        //refreshTokenCookie.setSecure(false);   // HTTPS에서만 전송. ssl 설정 완료 후 주석해제 필요
        refreshTokenCookie.setPath("/");      // 전체 도메인에서 접근 가능
        refreshTokenCookie.setMaxAge(60 * 60 * 24); // 쿠키 만료 시간 (1일)

        return refreshTokenCookie;
    }

    /**
     * Refresh Token 쿠키 삭제
     */
    private void deleteRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
