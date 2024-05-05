package kr.co.yourplanet.online.business.portfolio.repository;

import kr.co.yourplanet.core.entity.portfolio.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findAllByCategoryCodeIn(List<String> categoryCodeList);
}
