package kr.co.yourplanet.ypbackend.business.user.service;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.dto.LoginForm;
import kr.co.yourplanet.ypbackend.business.user.repository.MemberRepository;
import kr.co.yourplanet.ypbackend.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String register(Member member) {
        //중복 아이디 체크. 중복시 Exception 발생
        //checkDuplicateId(member.getId());

        // 비밀번호 유효성 체크
        validatePassword(member.getPassword());

        // 추후 비밀번호 저장시 암호화해서 DB에 저장하는 로직 추가하자
        //memberRepository.saveMember(member);

        return "Done";
    }

    public String login(LoginForm loginForm) {
        Member member = memberRepository.findMemberById(loginForm.getId());

        if (member == null || !member.getPassword().equals(loginForm.getPassword())) {
            throw new IllegalStateException("아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.");
        }

        return jwtTokenProvider.createToken(member.getId(), member.getName(), member.getMemberType());
    }

    private void checkDuplicateId(String id) {
        if (memberRepository.findMemberById(id) != null) {
            throw new IllegalStateException("이미 존재하는 ID입니다");
        }
    }

    private void validatePassword(String password) {
        final int MIN_LENGTH = 8;
        final int MAX_LENGTH = 20;

        final String LOWER_CASE_REGEX = "^.*[a-z]$";
        final String UPPER_CASE_REGEX = "^(?=.*[A-Z])$";
        final String DIGIT_REGEX = "^(?=.*[0-9])$";


        // 길이 체크
        if (Pattern.matches(LOWER_CASE_REGEX, password)) {
            log.info("LOWER_CASE_REGEX matched !");
        } else {
            log.error("LOWER_CASE_REGEX not matched !");
        }

        if (Pattern.matches(UPPER_CASE_REGEX, password)) {
            log.info("UPPER_CASE_REGEX matched !");
        } else {
            log.error("LOWER_CASE_REGEX not matched !");
        }
        if (Pattern.matches(DIGIT_REGEX, password)) {
            log.info("DIGIT_REGEX matched !");
        } else {
            log.error("LOWER_CASE_REGEX not matched !");
        }

    }
}
