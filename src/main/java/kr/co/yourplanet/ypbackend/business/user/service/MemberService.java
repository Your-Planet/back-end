package kr.co.yourplanet.ypbackend.business.user.service;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.dto.LoginForm;
import kr.co.yourplanet.ypbackend.business.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public String register(Member member){
        //중복 아이디 체크. 중복시 Exception 발생
        checkDuplicateId(member.getId());

        // 추후 비밀번호 저장시 암호화해서 DB에 저장하는 로직 추가하자
        memberRepository.saveMember(member);

        return "Done";
    }

    public void checkDuplicateId(String id){
        if(memberRepository.findMemberById(id) != null){
            throw new IllegalStateException("이미 존재하는 ID입니다");
        }
    }

    public Member login(LoginForm loginForm) {
        Member member = memberRepository.findMemberById(loginForm.getId());

        if (member == null) {
            throw new IllegalStateException("존재하지 않는 ID입니다.");
        }

        if (!member.getPassword().equals(loginForm.getPassword())) {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }

        return member;
    }

}
