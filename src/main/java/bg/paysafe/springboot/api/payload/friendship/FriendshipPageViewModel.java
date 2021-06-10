package bg.paysafe.springboot.api.payload.friendship;

import java.util.List;

public class FriendshipPageViewModel {

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages;

    private Long totalElements;

    private List<FriendshipUsersInfoViewModel> friendships;

    public FriendshipPageViewModel(Integer pageNumber, Integer pageSize, Integer totalPages, Long totalElements, List<FriendshipUsersInfoViewModel> friendships) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.friendships = friendships;
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

    public List<FriendshipUsersInfoViewModel> getFriendships() {
        return this.friendships;
    }
}
