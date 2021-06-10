package bg.paysafe.springboot.api.payload.chat;

import bg.paysafe.springboot.api.entity.Message;

import java.util.Date;

public class MessageInfoViewModel {

    private Long id;

    private Long userId;

    private Long conversationId;

    private String message;

    private Boolean seen;

    private Date createdAt;

    private MessageInfoViewModel() {
    }

    private MessageInfoViewModel(Long id, Long userId, Long conversationId, String message, Boolean isSeen, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.conversationId = conversationId;
        this.message = message;
        this.seen = isSeen;
        this.createdAt = createdAt;
    }

    public static MessageInfoViewModel ofMessage(Message message) {
        if (message == null)
            return new MessageInfoViewModel();

        return new MessageInfoViewModel(
                message.getId(),
                message.getSender().getId(),
                message.getConversation().getId(),
                message.getMessage(),
                message.isSeen(),
                message.getCreatedAt()
        );
    }

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getConversationId() {
        return this.conversationId;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Boolean isSeen() {
        return this.seen;
    }

}
