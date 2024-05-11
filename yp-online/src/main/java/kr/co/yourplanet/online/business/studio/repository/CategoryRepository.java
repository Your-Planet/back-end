package kr.co.yourplanet.online.business.studio.repository;

import kr.co.yourplanet.core.entity.studio.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findAllByCategoryCodeIn(List<String> categoryCodeList);
}
