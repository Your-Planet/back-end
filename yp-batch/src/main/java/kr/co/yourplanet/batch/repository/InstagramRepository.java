package kr.co.yourplanet.batch.repository;

import kr.co.yourplanet.batch.domain.MemberInstagramInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InstagramRepository {

    private final EntityManager em;

    public List<MemberInstagramInfo> findAllMemberInstagramInfo() {
        return em.createQuery("select mi from MemberInstagramInfo mi", MemberInstagramInfo.class)
                .getResultList();
    }
}
