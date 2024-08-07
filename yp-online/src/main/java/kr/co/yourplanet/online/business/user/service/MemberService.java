package kr.co.yourplanet.online.business.user.service;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.MemberSalt;
import kr.co.yourplanet.core.entity.member.RefreshToken;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.user.dto.*;
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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private static final String LOWER_CASE_REGEX = ".*[a-z].*";
    private static final String UPPER_CASE_REGEX = ".*[A-Z].*";
    private static final String DIGIT_REGEX = ".*\\d.*";
    private static final String SPECIAL_CHARACTER_REGEX = ".*[^a-zA-Z0-9].*";
    private static final String REPEATED_CHARACTERS_REGEX = ".*(.)\\1{2,}.*";
    private static final String SEQUENTIAL_REGEX = ".*([a-zA-Z]{3}|\\d{3}).*";
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    private final MemberRepository memberRepository;
    private final MemberSaltRepository memberSaltRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final EncryptManager encryptManager;

    @Transactional
    public void join(JoinForm joinForm) {

        // 공통 체크
        checkDuplicateEmail(joinForm.getEmail()); //중복 이메일 체크. 중복시 Exception 발생
        validatePassword(joinForm.getPassword()); // 비밀번호 정책 확인

        if (joinForm.getTermsInfo().getIsTermsOfService() == null || !joinForm.getTermsInfo().getIsTermsOfService()
                || joinForm.getTermsInfo().getIsPrivacyPolicy() == null || !joinForm.getTermsInfo().getIsPrivacyPolicy()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "필수 약관에 동의해주세요.", false);
        }

        // 작가 인스타그램 계정 기가입 여부 확인
        if (MemberType.CREATOR.equals(joinForm.getMemberType()) && memberRepository.findByInstagramId(joinForm.getInstagramId()).isPresent()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "이미 가입된 인스타그램 계정입니다.", false);
        }

        // 비밀번호 암호화
        String salt = encryptManager.generateSalt();
        String encodedHashPassword = encryptManager.encryptPassword(joinForm.getPassword(), salt);

        Member member = Member.builder()
                .email(joinForm.getEmail())
                .password(encodedHashPassword)
                .name(joinForm.getName())
                .genderType(joinForm.getGenderType())
                .tel(joinForm.getTel())
                .memberType(joinForm.getMemberType())
                .birthDate(joinForm.getBirthDate())
                .instagramId(joinForm.getInstagramId())
                .instagramUsername(joinForm.getInstagramUsername())
                .instagramAccessToken(joinForm.getInstagramAccessToken())
                .companyName(joinForm.getCompanyName())
                .businessNumber(joinForm.getBusinessNumber())
                .representativeName(joinForm.getRepresentativeName())
                .businessAddress(joinForm.getBusinessAddress())
                .termsOfServiceAgreedTime(joinForm.getTermsInfo().getIsTermsOfService() ? LocalDateTime.now() : null)
                .privacyPolicyAgreedTime(joinForm.getTermsInfo().getIsPrivacyPolicy() ? LocalDateTime.now() : null)
                .shoppingInformationAgreedTime(joinForm.getTermsInfo().getIsShoppingInformation() ? LocalDateTime.now() : null)
                .build();

        memberRepository.saveMember(member);

        MemberSalt memberSalt = MemberSalt.builder()
                .member(member)
                .salt(encryptManager.encryptSalt(salt))
                .build();
        memberSaltRepository.saveMemberSalt(memberSalt);

    }

    public void checkDuplicateEmail(String email) {

        if (memberRepository.findMemberByEmail(email).isPresent()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "중복된 이메일이 존재합니다.", false);
        }

    }

    @Transactional
    public RefreshTokenForm login(LoginForm loginForm) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(loginForm.getEmail());

        if (!findMember.isPresent()) {
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

    public void validatePassword(String password) {

        int patternCount = 0;

        // 비밀번호 길이 체크
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "8-20자의 비밀번호만 사용할 수 있어요", false);
        }

        if (Pattern.matches(LOWER_CASE_REGEX, password)) {
            patternCount++;
        }
        if (Pattern.matches(UPPER_CASE_REGEX, password)) {
            patternCount++;
        }
        if (Pattern.matches(DIGIT_REGEX, password)) {
            patternCount++;
        }
        if (Pattern.matches(SPECIAL_CHARACTER_REGEX, password)) {
            patternCount++;
        }

        // 비밀번호 패턴 종류 체크
        if (patternCount < 3) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "영문 대문자, 소문자, 숫자, 특수문자 중 3종류 이상을 사용해 주세요.", false);
        }

 /*
        if (Pattern.matches(REPEATED_CHARACTERS_REGEX, password)) {
            throw new BuisnessException("동일한 문자/숫자는 3자리 이상 사용할 수 없어요.");
        }

        if (Pattern.matches(SEQUENTIAL_REGEX, password)){
            throw new BuisnessException("3자리 연속된 문자/숫자는 비밀번호로 사용할 수 없어요.");
        }
*/
    }

    public String findId(FindIdForm accountRecoveryFrom) {
        Optional<Member> findMember = memberRepository.findByNameAndPhone(accountRecoveryFrom.getName(), accountRecoveryFrom.getTel());

        if (!findMember.isPresent()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "가입된 회원이 없습니다.", false);
        }

        return findMember.get().getEmail();
    }

    @Transactional
    public void resetPassword(ResetPasswordForm resetPasswordForm) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(resetPasswordForm.getEmail());
        MemberSalt memberSalt;

        if (!findMember.isPresent()) {
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
        validatePassword(resetPasswordForm.getNewPassword());

        // 비밀번호 암호화
        String salt = encryptManager.generateSalt();
        String encodedHashPassword = encryptManager.encryptPassword(resetPasswordForm.getNewPassword(), salt);

        member.updatePassword(encodedHashPassword);

        memberRepository.saveMember(member);

        Optional<MemberSalt> findMemberSalt = memberSaltRepository.findByMember(member);
        if (!findMemberSalt.isPresent()) {
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

    public void validateMember(MemberValidateForm memberValidateForm) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(memberValidateForm.getEmail());

        if (!findMember.isPresent()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "해당 이메일로 가입된 회원이 없습니다.", false);
        }

        Member member = findMember.get();
        if (!memberValidateForm.getTel().equals(member.getTel())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "전화번호가 일치하지 않습니다.", false);
        }

        if (!memberValidateForm.getName().equals(member.getName())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "사용자 이름이 일치하지 않습니다.", false);
        }
    }

    public MemberDetail getMemberDetailInfo(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        if (!findMember.isPresent()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "해당 회원이 존재하지 않습니다.", false);
        }

        Member member = findMember.get();

        TermsInfo termsInfo = TermsInfo.builder()
                .isTermsOfService(member.getTermsOfServiceAgreedTime() != null)
                .isPrivacyPolicy(member.getPrivacyPolicyAgreedTime() != null)
                .isShoppingInformation(member.getShoppingInformationAgreedTime() != null)
                .build();

        return MemberDetail.builder()
                .email(member.getEmail())
                .name(member.getName())
                .genderType(member.getGenderType())
                .tel(member.getTel())
                .memberType(member.getMemberType())
                .instagramUsername(member.getInstagramUsername())
                .termsInfo(termsInfo)
                .build();
    }

    @Transactional
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
