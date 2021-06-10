package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {

    @Query("select g from Gender g where g.id IN (?1)")
    Set<Gender> findAllById(Collection<Long> ids);

    @Query("SELECT g FROM Gender g ORDER BY g.id")
    Set<Gender> findAllAndSortById();

}
