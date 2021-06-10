package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Page<Friendship> findAllBySenderAndStatusId(Long userId, Long statusId, Pageable pageable);

    Page<Friendship> findAllByRecipientAndStatusId(Long userId, Long statusId, Pageable pageable);

    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.sender = ?1 OR f.recipient = ?1) " +
            "AND (f.sender = ?2 OR f.recipient = ?2)")
    Friendship findByIds(Long userId, Long friendId);

    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.sender = ?1 " +
            "OR f.recipient = ?1)")
    List<Friendship> findAllByUserId(Long id);

}
