package bg.paysafe.springboot.api.payload.chat;

import bg.paysafe.springboot.api.entity.Conversation;
import bg.paysafe.springboot.api.entity.Message;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.stream.Collectors;

public class ConversationViewModel {

    private String name;

    private Date startedAt;

    private List<MessageInfoViewModel> messages;

    private List<UserConversationInfoViewModel> users;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages;

    private Long totalElements;

    public ConversationViewModel(String name, Date startedAt, List<MessageInfoViewModel> messages, List<UserConversationInfoViewModel> users, Integer pageNumber, Integer pageSize, Integer totalPages, Long totalElements) {
        this.name = name;
        this.startedAt = startedAt;
        this.messages = messages;
        this.users = users;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public static ConversationViewModel ofConversation(Conversation conversation, Page<Message> messages) {
        final var content = messages.map(MessageInfoViewModel::ofMessage).getContent();
        return new ConversationViewModel(
                conversation.getName(),
                conversation.getStartedAt(),
                content,
                conversation.getUsers().stream().map(UserConversationInfoViewModel::ofUserConversation).collect(Collectors.toList()),
                messages.getNumber(),
                messages.getSize(),
                messages.getTotalPages(),
                messages.getTotalElements()
        );
    }

    public String getName() {
        return this.name;
    }

    public Date getStartedAt() {
        return this.startedAt;
    }

    public List<MessageInfoViewModel> getMessages() {
        return this.messages;
    }

    public List<UserConversationInfoViewModel> getUsers() {
        return this.users;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public Long getTotalElements() {
        return this.totalElements;
    }
}
