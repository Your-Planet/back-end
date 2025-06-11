package kr.co.yourplanet.online.business.user.service;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.MemberSalt;
import kr.co.yourplanet.core.entity.member.RefreshToken;
import kr.co.yourplanet.core.enums.AuthPurpose;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.user.dto.request.ChangePasswordForm;
import kr.co.yourplanet.online.business.user.dto.request.LoginForm;
import kr.co.yourplanet.online.business.user.dto.request.RefreshTokenForm;
import kr.co.yourplanet.online.business.user.dto.request.ResetPasswordForm;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.business.user.repository.MemberSaltRepository;
import kr.co.yourplanet.online.business.user.repository.RefreshTokenRepository;
import kr.co.yourplanet.online.common.encrypt.EncryptManager;
import kr.co.yourplanet.online.common.exception.BadRequestException;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.infra.redis.RedisTokenRepository;
import kr.co.yourplanet.online.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberValidationService memberValidationService;
    private final MemberQueryService memberQueryService;
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;
    private final MemberSaltRepository memberSaltRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTokenRepository redisTokenRepository;

    private final EncryptManager encryptManager;

    public RefreshTokenForm login(LoginForm loginForm) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(loginForm.getEmail());

        if (findMember.isEmpty()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "잘못된 이메일 혹은 비밀번호입니다.", false);
        }

        Member member = findMember.get();

        // 입력받은 비밀번호 암호화 후 비교
        String encryptedSalt = member.getMemberSalt().getSalt();
        String encryptedPassword = encryptManager.encryptPassword(loginForm.getPassword(), encryptManager.decryptSalt(encryptedSalt));
        if (!member.getPassword().equals(encryptedPassword)) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "잘못된 이메일 혹은 비밀번호입니다.", false);
        }

        // Access, Refresh 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId(), member.getName(), member.getMemberType());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        // Refresh 토큰 DB 저장
        RefreshToken refreshTokenEntity = refreshTokenRepository.findById(member.getId())
                .orElse(RefreshToken.builder()
                        .member(member)
                        .build());
        refreshTokenEntity.updateRefreshToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return RefreshTokenForm.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void changePassword(long memberId, ChangePasswordForm form) {
        Member member = memberQueryService.getById(memberId);
        String rawSalt = encryptManager.decryptSalt(member.getMemberSalt().getSalt());

        updateMemberPassword(member, form.password(), rawSalt);
    }

    public void resetPassword(ResetPasswordForm form) {
        // 토큰 정보 조회
        Long memberId = redisTokenRepository.getMemberId(AuthPurpose.PASSWORD_RESET, form.getToken())
                .orElseThrow(() -> new BadRequestException("토큰에 해당하는 정보가 존재하지 않습니다."));
        Member member = memberQueryService.getById(memberId);

        // 새 salt 생성
        String rawSalt = encryptManager.generateSalt();
        String encryptedSalt = encryptManager.encryptSalt(rawSalt);

        updateMemberPassword(member, form.getNewPassword(), rawSalt);
        upsertMemberSalt(member, encryptedSalt);
    }

    public RefreshTokenForm refreshAccessToken(String previousRefreshToken) {
        Long memberId;
        try {
            memberId = jwtTokenProvider.getMemberIdFromRefreshToken(previousRefreshToken);
            RefreshToken refreshTokenEntity = refreshTokenRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.UNAUTHORIZED, "재로그인이 필요합니다.", false));
            if (StringUtils.hasText(refreshTokenEntity.getRefreshToken()) && previousRefreshToken.equals(refreshTokenEntity.getRefreshToken())) {
                Member member = refreshTokenEntity.getMember();
                String accessToken = jwtTokenProvider.createAccessToken(member.getId(), member.getName(), member.getMemberType());
                String refreshToken = jwtTokenProvider.createRefreshToken(memberId);
                refreshTokenEntity.updateRefreshToken(refreshToken);
                refreshTokenRepository.save(refreshTokenEntity);

                return RefreshTokenForm.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            } else {
                refreshTokenEntity.deleteRefreshToken();
                refreshTokenRepository.save(refreshTokenEntity);
                return RefreshTokenForm.builder()
                        .refreshToken("")
                        .accessToken("")
                        .build();
            }
        } catch (Exception e) {
            throw new BusinessException(StatusCode.UNAUTHORIZED, "재로그인이 필요합니다.", false);
        }
    }

    private void updateMemberPassword(Member member, String password, String salt) {
        memberValidationService.validatePasswordFormat(password);
        memberValidationService.validatePasswordReused(member.getId(), password);

        String encodedPassword = encryptManager.encryptPassword(password, salt);
        member.updatePassword(encodedPassword);
        memberRepository.saveMember(member);
    }

    private void upsertMemberSalt(Member member, String encryptedSalt) {
        MemberSalt memberSalt = memberSaltRepository.findByMember(member)
                .map(existing -> {
                    existing.updateSalt(encryptedSalt);
                    return existing;
                })
                .orElseGet(() -> MemberSalt.builder()
                        .member(member)
                        .salt(encryptedSalt)
                        .build());

        memberSaltRepository.saveMemberSalt(memberSalt);
    }
}
