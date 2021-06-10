package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.UploadType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadTypeRepository extends JpaRepository<UploadType, Long> {

    @Query("SELECT u FROM UploadType u WHERE u.name = ?1")
    UploadType findByName(String name);

}
