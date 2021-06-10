package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.UserVisit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVisitRepository extends JpaRepository<UserVisit, Long> {

    @Query("SELECT u FROM UserVisit u WHERE u.user.id = ?1")
    Page<UserVisit> findAllByVisitorId(Long userId, Pageable pageable);

    @Query("SELECT u FROM UserVisit u WHERE u.visitedUser.id = ?1")
    Page<UserVisit> findAllByVisitedId(Long userId, Pageable pageable);

    @Query("SELECT u FROM UserVisit u WHERE (u.user.id = ?1 OR u.visitedUser.id = ?1)")
    List<UserVisit> findByUserId(Long id);

    UserVisit findByUserIdAndVisitedUserId(Long userId, Long visitedId);

    @Query("DELETE FROM UserVisit uv WHERE uv.user.id = ?1")
    void deleteAllByUserId(Long id);

}
