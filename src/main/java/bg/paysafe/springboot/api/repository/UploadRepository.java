package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.Upload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Long> {

    @Query("SELECT u FROM Upload u LEFT JOIN u.user uu WHERE u.typeId = ?2 AND uu.id = ?1")
    List<Upload> findAllByUserAndTypeId(Long userId, Long typeId);

    @Query("SELECT u FROM Upload u " +
            "LEFT JOIN u.user uu " +
            "WHERE u.typeId = ?2 " +
            "AND uu.id = ?1 " +
            "AND u.video = false")
    Page<Upload> findAllByUserAndTypeIdWithPaging(Long userId, Long typeId, Pageable pageable);

    @Query("SELECT u FROM Upload u " +
            "LEFT JOIN u.user uu " +
            "WHERE u.id = ?1 " +
            "AND uu.id = ?2")
    Optional<Upload> findByIdAndUserId(Long uploadId, Long userId);

}
