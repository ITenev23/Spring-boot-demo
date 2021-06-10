package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {

    @Query("SELECT c FROM CategoryItem c WHERE c.category.id = ?1")
    List<CategoryItem> findAllByCategoryId(Long id);

}
