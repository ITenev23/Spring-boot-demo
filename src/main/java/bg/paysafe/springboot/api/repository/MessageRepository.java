package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m LEFT JOIN m.conversation c WHERE c.id = ?1")
    Page<Message> findAllByConversationId(Long id, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.id = ?1 AND m.sender.id = ?2")
    Optional<Message> findByIdAndUserId(Long messageId, Long userId);

}
