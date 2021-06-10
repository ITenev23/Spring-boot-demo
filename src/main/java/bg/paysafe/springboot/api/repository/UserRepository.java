package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByUsername(String username);

    Optional<User> findFirstByEmailOrUsername(String email, String username);

    @Query("SELECT f FROM User u LEFT JOIN u.friends f WHERE u.id = ?1")
    Page<User> findAllFriendsByUserId(Long id, Pageable pageable);

    @Query("SELECT f FROM User u LEFT JOIN u.favorites f WHERE u.id = ?1")
    Page<User> findAllFavoritesByUserId(Long id, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.status.id = ?1")
    Page<User> findAllByStatusId(Long id, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id <> ?1 AND u.id <> ?2 AND u.status.id = 1")
    Page<User> findAllByRegisteredDate(Long supportId, Long userId, Pageable pageable);

    @Query("SELECT u FROM User u WHERE ?1 IN (u.favorites)")
    List<User> findAllUserByFavoriteUser(User u);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN u.gendersSearch ug " +
            "WHERE u.id <> ?1 " +
            "AND u.id <> ?2 " +
            "AND u.username = COALESCE(?3, u.username) " +
            "AND u.gender.id = COALESCE(?4, u.gender.id) " +
            "AND ug.id = COALESCE(?5, ug.id) " +
            "AND u.status.id = 1 " +
            "AND (u.age BETWEEN COALESCE(?6, u.age) AND COALESCE(?7, u.age)) " +
            "AND u.city.id = COALESCE(?8, u.city.id) " +
            "AND u.country.id = COALESCE(?9, u.country.id) " +
            "GROUP BY u.id")
    Page<User> findAllBySearchCritеria(
            Long supportId,
            Long userId,
            String username,
            Long genderId,
            Long genderSearchId,
            Integer ageMin,
            Integer ageMax,
            Long cityId,
            Long countryId,
            Pageable pageable
            );

}
