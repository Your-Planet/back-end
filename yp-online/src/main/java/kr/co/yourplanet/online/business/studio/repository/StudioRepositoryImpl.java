package kr.co.yourplanet.online.business.studio.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import kr.co.yourplanet.core.entity.studio.Category;
import kr.co.yourplanet.online.business.studio.dao.StudioBasicDao;

@Repository
public class StudioRepositoryImpl implements StudioRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public List<StudioBasicDao> findStudioBasicsWithFilters(List<Category> categories, String toonName, String description, String instagramUsername, Integer minPrice, Integer maxPrice, Pageable pageable) {

        // 1-1 조회조건에 맞는 프로필ID 조회
        StringBuilder findProfileIdsQuery = new StringBuilder("select distinct s.id " +
                "from Profile s " +
                "join ProfileCategoryMap scm on s.id = scm.profile.id " +
                "join Member m on s.member.id = m.id " +
                "join Price p on p.profile.id = s.id and p.isLatest = true");

        // 1-2 조회조건 추가
        List<String> conditions = new ArrayList<>();
        if (StringUtils.hasText(toonName)) {
            conditions.add("s.toonName like concat('%', :toonName, '%')");
        }
        if (StringUtils.hasText(description)) {
            conditions.add("s.description like concat('%', :description, '%')");
        }
        if (StringUtils.hasText(instagramUsername)) {
            conditions.add("m.instagramInfo.instagramUsername like concat('%', :instagramUsername, '%')");
        }
        if (!CollectionUtils.isEmpty(categories)) {
            conditions.add("exists (select 1 from ProfileCategoryMap scm2 where scm.profile = scm2.profile and scm2.category in :categories)");
        }
        if (minPrice != null) {
            conditions.add("p.price >= :minPrice");
        }
        if (maxPrice != null) {
            conditions.add("p.price <= :maxPrice");
        }
        if (!CollectionUtils.isEmpty(conditions)) {
            findProfileIdsQuery.append(" where ");
            findProfileIdsQuery.append(String.join(" and ", conditions));
        }

        TypedQuery<Long> studioQuery = entityManager.createQuery(findProfileIdsQuery.toString(), Long.class);

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
        if (minPrice != null) {
            studioQuery.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            studioQuery.setParameter("maxPrice", maxPrice);
        }

        // 1.3 Paging 처리
        studioQuery.setFirstResult((int) pageable.getOffset());
        studioQuery.setMaxResults(pageable.getPageSize());

        List<Long> profileIds = studioQuery.getResultList();

        // 2.1 스튜디오 기본 정보 조회
        String studioBasicQuery = "select new kr.co.yourplanet.online.business.studio.dao.StudioBasicDao(m.id, s.toonName, s.description, s.profileImageFile.id, m.instagramInfo.instagramUsername, scm.category.categoryCode) " +
                "from Profile s " +
                "join ProfileCategoryMap scm on s.id = scm.profile.id " +
                "join Member m on s.member.id = m.id " +
                "join Price p on p.profile.id = s.id and p.isLatest = true " +
                "where s.id in :profileIds";

        return entityManager.createQuery(studioBasicQuery, StudioBasicDao.class)
                .setParameter("profileIds", profileIds)
                .getResultList();
    }
}
