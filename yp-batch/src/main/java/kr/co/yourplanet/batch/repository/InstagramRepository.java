package kr.co.yourplanet.batch.repository;

import kr.co.yourplanet.core.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InstagramRepository {

    private final EntityManager em;

    public List<Member> findAllMemberInstagramInfo() {
        return em.createQuery("select m from Member m where memberType = 1", Member.class)
                .getResultList();
    }
}
