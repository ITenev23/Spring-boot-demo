package bg.paysafe.springboot.api.payload.chat;

import bg.paysafe.springboot.api.entity.Conversation;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FriendsMessagesViewModel {

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages;

    private Long totalElements;

    private List<FriendsMessages> conversations;

    public FriendsMessagesViewModel() {
        this.conversations = new ArrayList<>();
    }

    public static FriendsMessagesViewModel ofFriendsMessages(Page<Conversation> p) {
        var model = new FriendsMessagesViewModel();
        model.setPageNumber(p.getNumber());
        model.setPageSize(p.getSize());
        model.setTotalPages(p.getTotalPages());
        model.setTotalElements(p.getTotalElements());
        model.setConversations(p.getContent().stream().map(FriendsMessages::new).collect(Collectors.toList()));

        return model;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<FriendsMessages> getConversations() {
        return this.conversations;
    }

    public void setConversations(List<FriendsMessages> conversations) {
        this.conversations = conversations;
    }

}
