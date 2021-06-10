package bg.paysafe.springboot.api.repository;

import bg.paysafe.springboot.api.entity.Conversation;
import bg.paysafe.springboot.api.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c LEFT JOIN c.users cu LEFT JOIN c.lastMessage m WHERE cu.id = ?1 GROUP BY c.id")
    Page<Conversation> findAllConversationsWithLastMessageByUserId(Long id, Pageable pageable);

    @Query("SELECT c FROM Conversation c LEFT JOIN c.users cu WHERE cu.id = ?1 AND c.id = ?2")
    Optional<Conversation> findByIdAndUserId(Long userId, Long conversationId);

    @Query("SELECT cm FROM Conversation c " +
            "LEFT JOIN c.messages cm " +
            "LEFT JOIN c.users u " +
            "WHERE cm.seen = false " +
            "AND u.id = ?1 " +
            "AND cm.sender.id <> ?1")
    List<Message> findAllUnreadMessagesByUserId(Long userId);

    @Query("SELECT cm FROM Conversation c " +
            "LEFT JOIN c.messages cm " +
            "LEFT JOIN c.users u " +
            "WHERE cm.seen = false " +
            "AND cm.id IN (?1) " +
            "AND u.id = ?2 " +
            "AND cm.sender.id <> ?2")
    List<Message> findAllMessagesByIdAndUserId(Iterable<Long> ids, Long userId);

    @Query("SELECT c FROM Conversation c WHERE c.id IN (select cc.id from Conversation cc left join cc.users u where u.id = ?1) and c.id IN (select cc.id from Conversation cc left join cc.users u where u.id = ?2)")
    Conversation findByUsersId(Long userId, Long friendId);

}
