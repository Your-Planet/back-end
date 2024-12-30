package kr.co.yourplanet.online.business.user.repository;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.MemberSalt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberSaltRepository {
    private final EntityManager em;

    public void saveMemberSalt(MemberSalt memberSalt){
        em.persist(memberSalt);
    }

    public Optional<MemberSalt> findByMember(Member member) {
        return em.createQuery("select m from MemberSalt m where m.member = :member", MemberSalt.class)
                .setParameter("member", member)
                .getResultStream().findAny();
    }
}
