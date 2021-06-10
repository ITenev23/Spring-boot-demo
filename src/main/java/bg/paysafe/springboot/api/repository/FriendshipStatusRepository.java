package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipStatusRepository extends JpaRepository<FriendshipStatus, Long> {
}
