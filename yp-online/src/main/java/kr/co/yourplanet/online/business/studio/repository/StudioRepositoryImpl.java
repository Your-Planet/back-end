package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.Category;
import kr.co.yourplanet.online.business.studio.dao.StudioBasicDao;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudioRepositoryImpl implements StudioRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public List<StudioBasicDao> findStudioBasicsWithFilters(List<Category> categories, String toonName, String description, String instagramUsername, Pageable pageable) {

        // 1-1 조회조건에 맞는 스튜디오ID 조회
        StringBuilder findStduioIdsQuery = new StringBuilder("select distinct s.id " +
                "from Studio s " +
                "join PortfolioCategoryMap pcm on s.id = pcm.studio.id " +
                "join Member m on s.id = m.id " +
                "join Price p on s.id = p.id ");

        // 1-2 조회조건 추가
        List<String> conditions = new ArrayList<>();
        if (StringUtils.hasText(toonName)) {
            conditions.add("s.toonName like concat('%', :toonName, '%')");
        }
        if (StringUtils.hasText(description)) {
            conditions.add("s.description like concat('%', :description, '%')");
        }
        if (StringUtils.hasText(instagramUsername)) {
            conditions.add("m.instagramUsername like concat('%', :instagramUsername, '%')");
        }
        if (!CollectionUtils.isEmpty(categories)) {
            conditions.add("exists (select 1 from PortfolioCategoryMap pcm2 where pcm.studio = pcm2.studio and pcm2.category in :categories)");
        }
        if (!CollectionUtils.isEmpty(conditions)) {
            findStduioIdsQuery.append(" where ");
            findStduioIdsQuery.append(String.join(" and ", conditions));
        }

        TypedQuery<Long> studioQuery = entityManager.createQuery(findStduioIdsQuery.toString(), Long.class);

        if (StringUtils.hasText(toonName)) {
            studioQuery.setParameter("toonName", toonName);
        }
        if (StringUtils.hasText(description)) {
            studioQuery.setParameter("description", description);
        }
        if (StringUtils.hasText(instagramUsername)) {
            studioQuery.setParameter("instagramUsername", instagramUsername);
        }
        if (!CollectionUtils.isEmpty(categories)) {
            studioQuery.setParameter("categories", categories);
        }

        // 1.3 Paging 처리
        studioQuery.setFirstResult((int) pageable.getOffset());
        studioQuery.setMaxResults(pageable.getPageSize());

        List<Long> studiosIds = studioQuery.getResultList();

        // 2.1 스튜디오 기본 정보 조회
        String studioBasicQuery = "select new kr.co.yourplanet.online.business.studio.dao.StudioBasicDao(s.id, s.toonName, s.description, m.instagramUsername, pcm.category.categoryName) " +
                "from Studio s " +
                "join PortfolioCategoryMap pcm on s.id = pcm.studio.id " +
                "join Member m on s.id = m.id " +
                "join Price p on s.id = p.id " +
                "where s.id in :studiosIds";

        return entityManager.createQuery(studioBasicQuery, StudioBasicDao.class)
                .setParameter("studiosIds", studiosIds)
                .getResultList();
    }

}
