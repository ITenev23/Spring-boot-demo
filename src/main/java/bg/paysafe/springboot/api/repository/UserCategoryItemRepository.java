package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.UserCategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCategoryItemRepository extends JpaRepository<UserCategoryItem, Long> {

    @Query("SELECT u FROM UserCategoryItem u WHERE u.user.id = ?1 AND u.items.category.id = ?2")
    List<UserCategoryItem> findAllByUserIdAndCategoryId(Long userId, Long categoryId);

    UserCategoryItem findByUserIdAndItemsId(Long userId, Long categoryItemId);

    @Query("SELECT u FROM UserCategoryItem u WHERE u.user.id = ?1")
    List<UserCategoryItem> findAllByUserId(Long id);

}
