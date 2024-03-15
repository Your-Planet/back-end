package kr.co.yourplanet.ypbackend.business.user.service;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.dto.LoginForm;
import kr.co.yourplanet.ypbackend.business.user.repository.MemberRepository;
import kr.co.yourplanet.ypbackend.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String register(Member member) {
        //중복 이메일 체크. 중복시 Exception 발생
        checkDuplicateEmail(member.getEmail());

        // 비밀번호 정책 확인
        validatePassword(member);

        // 추후 비밀번호 저장시 암호화해서 DB에 저장하는 로직 추가하자
        memberRepository.saveMember(member);

        return "Done";
    }

    public void checkDuplicateEmail(String email) {

        Optional<Member> findMember = memberRepository.findMemberByEmail(email);
        if (findMember.isPresent()) {
            throw new IllegalStateException("중복된 이메일이 존재합니다.");
        }

    }

    public String login(LoginForm loginForm) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(loginForm.getEmail());

        if (!findMember.isPresent() || !findMember.get().getPassword().equals(loginForm.getPassword())) {
            throw new IllegalStateException("아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.");
        }

        return jwtTokenProvider.createToken(findMember.get().getId(), findMember.get().getName(), findMember.get().getMemberType());
    }

    public void validatePassword(Member member) {
        String password = member.getPassword();

        int patternCount = 0;

        // 비밀번호 길이 체크
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new IllegalStateException("8-20자의 비밀번호만 사용할 수 있어요");
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
            throw new IllegalStateException("영문 대문자, 소문자, 숫자, 특수문자 중 3종류 이상을 사용해 주세요.");
        }

 /*
        if (Pattern.matches(REPEATED_CHARACTERS_REGEX, password)) {
            throw new IllegalStateException("동일한 문자/숫자는 3자리 이상 사용할 수 없어요.");
        }

        if (Pattern.matches(SEQUENTIAL_REGEX, password)){
            throw new IllegalStateException("3자리 연속된 문자/숫자는 비밀번호로 사용할 수 없어요.");
        }
*/
    }

}
