package bg.paysafe.springboot.api.payload.chat;

import bg.paysafe.springboot.api.entity.Conversation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FriendsMessages {

    private String conversationName;

    private Long conversationId;

    private Long messageId;

    private Long senderId;

    private String message;

    private Date date;

    private Boolean seen;

    private List<UserConversationInfoViewModel> users;

    public FriendsMessages(Conversation conversation) {
        this.conversationName = conversation.getName();
        this.conversationId = conversation.getId();
        this.senderId = conversation.getLastMessage().getSender().getId();
        this.messageId = conversation.getLastMessage().getId();
        this.message = conversation.getLastMessage().getMessage();
        this.date = conversation.getLastMessage().getCreatedAt();
        this.seen = conversation.getLastMessage().isSeen();
        this.users = conversation.getUsers().stream()
                .map(UserConversationInfoViewModel::ofUserConversation)
                .collect(Collectors.toList());
    }

    public String getConversationName() {
        return this.conversationName;
    }

    public Long getConversationId() {
        return this.conversationId;
    }

    public Long getSenderId() {
        return this.senderId;
    }

    public Long getMessageId() {
        return this.messageId;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getDate() {
        return this.date;
    }

    public Boolean isSeen() {
        return this.seen;
    }

    public List<UserConversationInfoViewModel> getUsers() {
        return this.users;
    }

}
