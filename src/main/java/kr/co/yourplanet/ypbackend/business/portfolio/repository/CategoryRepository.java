package kr.co.yourplanet.ypbackend.business.portfolio.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.Category;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    private final EntityManager em;

    public void saveCategory(Category category) {
        em.persist(category);
    }

    public Category findById(String categoryCode) {
        return em.find(Category.class, categoryCode);
    }

    public Optional<Category> findByCategoryName(String categoryName) {
        List<Category> categoryList = em.createQuery("select c from Category c where c.categoryName = :categoryName", Category.class)
        .setParameter("categoryName", categoryName)
        .getResultList();

        return categoryList.stream().findAny();
    }
}
