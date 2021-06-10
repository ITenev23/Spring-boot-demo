package bg.paysafe.springboot.api.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "conversations")
public class Conversation extends BaseEntity {

    private String name;

    private Date startedAt;

    private Message lastMessage;

    private List<Message> messages;

    private List<User> users;

    public Conversation() {
        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne
    public Message getLastMessage() {
        return this.lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    @OneToMany(mappedBy = "conversation")
    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Date getStartedAt() {
        return this.startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    @ManyToMany(mappedBy = "conversations")
    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Transient
    public void addMessage(Message message) {
        message.setConversation(this);
        this.messages.add(message);
    }

    @Transient
    public void addUser(User user) {
        user.addConversation(this);
        this.users.add(user);
    }

    @Transient
    public void setPreviousLastMessage() {
        if (!this.messages.isEmpty() && this.messages.size() >= 2)
            this.lastMessage = this.messages.get(this.messages.size() - 2);
        else
            this.lastMessage = null;
    }


}
