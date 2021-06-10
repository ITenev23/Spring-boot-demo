package bg.paysafe.springboot.api.payload.chat;

import bg.paysafe.springboot.api.entity.Conversation;

import java.util.List;
import java.util.stream.Collectors;

public class ConversationInfoViewModel {

    private String conversationName;

    private Long conversationId;

    private MessageInfoViewModel lastMessage;

    private List<UserConversationInfoViewModel> users;

    private ConversationInfoViewModel(
            String name,
            Long conversationId,
            List<UserConversationInfoViewModel> users
    ) {
        this.conversationId = conversationId;
        this.users = users;
    }

    private ConversationInfoViewModel(
            String name,
            Long conversationId,
            List<UserConversationInfoViewModel> users,
            MessageInfoViewModel lastMessage
            ) {
        this(name, conversationId, users);
        this.lastMessage = lastMessage;
    }

    public static ConversationInfoViewModel ofConversation(Conversation conversation) {
        final var lastMessage = conversation.getLastMessage();
        if (lastMessage == null)
            return new ConversationInfoViewModel(
                    conversation.getName(),
                    conversation.getId(),
                    conversation.getUsers().stream()
                            .map(UserConversationInfoViewModel::ofUserConversation)
                            .collect(Collectors.toList())
            );

        return new ConversationInfoViewModel(
                conversation.getName(),
                conversation.getId(),
                conversation.getUsers().stream()
                .map(UserConversationInfoViewModel::ofUserConversation)
                .collect(Collectors.toList()),
                MessageInfoViewModel.ofMessage(lastMessage)
                );
    }

    public String getConversationName() {
        return this.conversationName;
    }

    public Long getConversationId() {
        return this.conversationId;
    }

    public MessageInfoViewModel getLastMessage() {
        return this.lastMessage;
    }

    public List<UserConversationInfoViewModel> getUsers() {
        return this.users;
    }

}
