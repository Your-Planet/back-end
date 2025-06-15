package kr.co.yourplanet.online.business.user.repository;

import kr.co.yourplanet.core.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void saveMember(Member member) {
        if (member.getId() == null) {
            em.persist(member);
        } else {
            em.merge(member);
        }
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public Optional<Member> findMemberByEmail(String email) {
        return em.createQuery("select m from Member m where m.accountInfo.email = :email", Member.class)
                .setParameter("email", email)
                .getResultStream().findAny();
    }

    public Optional<Member> findMemberByTel(String tel) {
        return em.createQuery("select m from Member m where m.memberBasicInfo.tel = :tel", Member.class)
                .setParameter("tel", tel)
                .getResultStream().findAny();
    }

    public Optional<Member> findByInstagramId(String instagramId) {
        return em.createQuery("select m from Member m where m.instagramInfo.instagramId = :instagramId", Member.class)
                .setParameter("instagramId", instagramId)
                .getResultStream().findAny();
    }
}
