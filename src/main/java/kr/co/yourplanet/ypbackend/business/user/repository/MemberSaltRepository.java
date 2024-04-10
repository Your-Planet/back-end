package kr.co.yourplanet.ypbackend.business.user.repository;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.domain.MemberSalt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
