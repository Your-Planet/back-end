package kr.co.yourplanet.ypbackend.business.user.repository;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.domain.MemberSalt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void saveMember(Member member) {
        em.persist(member);
    }

    public Member findMemberById(Long id) {
        return em.find(Member.class, id);
    }

    public Optional<Member> findMemberByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultStream().findAny();
    }

    public Optional<Member> findByNameAndPhone(String name, String tel) {
        return em.createQuery("select m from Member m where m.name = :name and m.tel = :tel", Member.class)
                .setParameter("name", name)
                .setParameter("tel", tel)
                .getResultStream().findAny();
    }
}
