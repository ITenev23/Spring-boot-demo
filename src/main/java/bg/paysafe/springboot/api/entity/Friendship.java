package bg.paysafe.springboot.api.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friendships_requests")
public class Friendship extends BaseEntity {

    private Long sender;

    private Long recipient;

    private FriendshipStatus status;

    public Long getSender() {
        return this.sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getRecipient() {
        return this.recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    @ManyToOne
    public FriendshipStatus getStatus() {
        return this.status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

}
