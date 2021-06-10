package bg.paysafe.springboot.api.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

  private User sender;

  private String message;

  private Date createdAt;

  private Conversation conversation;

  private Boolean seen = false;

  @ManyToOne
  public User getSender() {
    return this.sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  @Column(columnDefinition = "TEXT")
  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @ManyToOne
  public Conversation getConversation() {
    return this.conversation;
  }

  public void setConversation(Conversation conversation) {
    this.conversation = conversation;
  }

  public Boolean isSeen() {
    return this.seen;
  }

  public void setSeen(Boolean seen) {
    this.seen = seen;
  }

  public Message see() {
    this.setSeen(true);
    return this;
  }
}
