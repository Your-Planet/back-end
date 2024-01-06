package kr.co.yourplanet.ypbackend.business.user.repository;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public void saveMember(Member member) {
        em.persist(member);
    }

    public Member findMemberById(String id) {
        return em.find(Member.class, id);
    }

    public Optional<Member> findMemberByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultStream().findAny();
    }
}
