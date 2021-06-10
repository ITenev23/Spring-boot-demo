package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.country.id = ?1 ORDER BY c.id ASC ")
    List<City> findAllByCountryId(Long id);

}
