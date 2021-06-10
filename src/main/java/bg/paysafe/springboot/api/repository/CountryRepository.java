package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
