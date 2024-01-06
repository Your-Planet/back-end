package kr.co.yourplanet.ypbackend.business.user.service;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.dto.LoginForm;
import kr.co.yourplanet.ypbackend.business.user.repository.MemberRepository;
import kr.co.yourplanet.ypbackend.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String register(Member member){
        //중복 이메일 체크. 중복시 Exception 발생
        checkDuplicateEmail(member.getEmail());

        // 추후 비밀번호 저장시 암호화해서 DB에 저장하는 로직 추가하자
        memberRepository.saveMember(member);

        return "Done";
    }

    public void checkDuplicateEmail(String email) {

        Optional<Member> findMember = memberRepository.findMemberByEmail(email);
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

    }

    public String login(LoginForm loginForm) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(loginForm.getEmail());

        if (!findMember.isPresent() || !findMember.get().getPassword().equals(loginForm.getPassword())) {
            throw new IllegalStateException("아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.");
        }

        return jwtTokenProvider.createToken(findMember.get().getId(), findMember.get().getName(), findMember.get().getMemberType());
    }

}
