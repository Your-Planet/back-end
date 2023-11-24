package kr.co.yourplanet.ypbackend.business.user.repository;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
}