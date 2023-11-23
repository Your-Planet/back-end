package kr.co.yourplanet.ypbackend.business.user.service;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.dto.LoginForm;
import kr.co.yourplanet.ypbackend.business.user.repository.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final HomeRepository homeRepository;

    @Transactional
    public String register(Member member){
        //중복 아이디 체크. 중복시 Exception 발생
        checkDuplId(member.getId());

        // 추후 비밀번호 저장시 암호화해서 DB에 저장하는 로직 추가하자

        homeRepository.saveMember(member);

        return "Done";
    }

    public void checkDuplId(String id){
        if(homeRepository.findMemberById(id) != null){
            throw new IllegalStateException("이미 존재하는 ID입니다");
        }
    }

    public Member login(LoginForm loginForm) {
        Member member = homeRepository.findMemberById(loginForm.getId());

        if (member == null) {
            return null;
        }

        if (!member.getPassword().equals(loginForm.getPassword())) {
            return null;
        }

        return member;
    }

}
