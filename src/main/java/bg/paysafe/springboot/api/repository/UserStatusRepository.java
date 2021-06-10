package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

    @Query("SELECT s FROM UserStatus s WHERE s.name = ?1")
    UserStatus findByName(String name);

}
