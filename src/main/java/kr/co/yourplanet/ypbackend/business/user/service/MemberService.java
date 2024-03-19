package kr.co.yourplanet.ypbackend.business.user.service;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.domain.MemberSalt;
import kr.co.yourplanet.ypbackend.business.user.dto.LoginForm;
import kr.co.yourplanet.ypbackend.business.user.dto.RegisterForm;
import kr.co.yourplanet.ypbackend.business.user.repository.MemberRepository;
import kr.co.yourplanet.ypbackend.common.exception.BusinessException;
import kr.co.yourplanet.ypbackend.common.encrypt.EncryptManager;
import kr.co.yourplanet.ypbackend.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final JwtTokenProvider jwtTokenProvider;

    private final EncryptManager encryptManager;

    @Transactional
    public String register(RegisterForm registerForm) {
        //중복 이메일 체크. 중복시 Exception 발생
        checkDuplicateEmail(registerForm.getEmail());

        // 비밀번호 정책 확인
        validatePassword(registerForm.getPassword());

        // 비밀번호 암호화
        String salt = encryptManager.generateSalt();
        String encodedHashPassword = encryptManager.encryptPassword(registerForm.getPassword(), salt);

        Member member = Member.builder()
                .email(registerForm.getEmail())
                .password(encodedHashPassword)
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

        memberRepository.saveMember(member);

        MemberSalt memberSalt = MemberSalt.builder()
                .member(member)
                .salt(encryptManager.encryptSalt(salt))
                .build();
        memberRepository.saveMemberSalt(memberSalt);

        return "Done";
    }

    public void checkDuplicateEmail(String email) {

        if (memberRepository.findMemberByEmail(email).isPresent()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "중복된 이메일이 존재합니다.", false);
        }

    }

    public String login(LoginForm loginForm) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(loginForm.getEmail());

        if (!findMember.isPresent()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "잘못된 아이디입니다. 입력하신 내용을 다시 확인해주세요.", false);
        }

        Member member = findMember.get();

        // 입력받은 비밀번호 암호화 후 비교
        String encryptedSalt = member.getMemberSalt().getSalt();
        String encryptedPassword = encryptManager.encryptPassword(loginForm.getPassword(), encryptManager.decryptSalt(encryptedSalt));
        if (!member.getPassword().equals(encryptedPassword)) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "잘못된 비밀번호입니다. 입력하신 내용을 다시 확인해주세요.", false);
        }

        return jwtTokenProvider.createToken(member.getId(), member.getName(), member.getMemberType());
    }

    public void validatePassword(String password) {

        int patternCount = 0;

        // 비밀번호 길이 체크
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new BusinessException("8-20자의 비밀번호만 사용할 수 있어요");
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
            throw new BusinessException("영문 대문자, 소문자, 숫자, 특수문자 중 3종류 이상을 사용해 주세요.");
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

}
