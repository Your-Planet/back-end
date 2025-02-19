package kr.co.yourplanet.online.business.user.service;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.user.dto.MemberValidateForm;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberValidationService {

    private static final String LOWER_CASE_REGEX = ".*[a-z].*";
    private static final String UPPER_CASE_REGEX = ".*[A-Z].*";
    private static final String DIGIT_REGEX = ".*\\d.*";
    private static final String SPECIAL_CHARACTER_REGEX = ".*[^a-zA-Z0-9].*";
    private static final String SEQUENTIAL_REGEX = ".*([a-zA-Z]{3}|\\d{3}).*";
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    private final MemberRepository memberRepository;

    public void checkDuplicateEmail(String email) {
        if (memberRepository.findMemberByEmail(email).isPresent()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "중복된 이메일이 존재합니다.", false);
        }
    }

    public void checkDuplicateInstagramId(String instagramId) {
        if (memberRepository.findByInstagramId(instagramId).isPresent()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "이미 가입된 인스타그램 계정입니다.", false);
        }
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

        // if (Pattern.matches(REPEATED_CHARACTERS_REGEX, password)) {
        //     throw new BuisnessException("동일한 문자/숫자는 3자리 이상 사용할 수 없어요.");
        // }
        //
        // if (Pattern.matches(SEQUENTIAL_REGEX, password)){
        //     throw new BuisnessException("3자리 연속된 문자/숫자는 비밀번호로 사용할 수 없어요.");
        // }
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
}
