package kr.co.yourplanet.online.business.user.service;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.MemberSalt;
import kr.co.yourplanet.core.entity.member.RefreshToken;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.user.dto.request.ChangePasswordForm;
import kr.co.yourplanet.online.business.user.dto.request.LoginForm;
import kr.co.yourplanet.online.business.user.dto.request.RefreshTokenForm;
import kr.co.yourplanet.online.business.user.dto.request.ResetPasswordForm;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.business.user.repository.MemberSaltRepository;
import kr.co.yourplanet.online.business.user.repository.RefreshTokenRepository;
import kr.co.yourplanet.online.common.encrypt.EncryptManager;
import kr.co.yourplanet.online.common.exception.BusinessException;
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
        memberValidationService.validatePasswordFormat(form.password());
        memberValidationService.validatePasswordReused(memberId, form.password());
        Member member = memberQueryService.getById(memberId);

        String decryptedSalt = encryptManager.decryptSalt(member.getMemberSalt().getSalt());
        String newPassword = encryptManager.encryptPassword(form.password(), decryptedSalt);

        member.updatePassword(newPassword);
        memberRepository.saveMember(member);
    }

    public void resetPassword(ResetPasswordForm resetPasswordForm) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(resetPasswordForm.getEmail());
        MemberSalt memberSalt;

        if (findMember.isEmpty()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "가입된 회원이 없습니다.", false);
        }

        Member member = findMember.get();
        if (!resetPasswordForm.getTel().equals(member.getTel())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "전화번호가 일치하지 않습니다.", false);
        }

        if (!resetPasswordForm.getName().equals(member.getName())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "사용자 이름이 일치하지 않습니다.", false);
        }

        // 비밀번호 정책 확인
        memberValidationService.validatePasswordFormat(resetPasswordForm.getNewPassword());

        // 비밀번호 암호화
        String salt = encryptManager.generateSalt();
        String encodedHashPassword = encryptManager.encryptPassword(resetPasswordForm.getNewPassword(), salt);

        member.updatePassword(encodedHashPassword);

        memberRepository.saveMember(member);

        Optional<MemberSalt> findMemberSalt = memberSaltRepository.findByMember(member);
        if (findMemberSalt.isEmpty()) {
            memberSalt = MemberSalt.builder()
                    .member(member)
                    .salt(encryptManager.encryptSalt(salt))
                    .build();
        } else {
            memberSalt = findMemberSalt.get();
            memberSalt.updateSalt(encryptManager.encryptSalt(salt));
        }
        memberSaltRepository.saveMemberSalt(memberSalt);
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
}
