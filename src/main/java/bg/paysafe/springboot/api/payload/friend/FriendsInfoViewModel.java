package bg.paysafe.springboot.api.payload.friend;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.payload.user.UserRequestInfoViewModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class FriendsInfoViewModel {

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages;

    private Long totalElements;

    private List<UserRequestInfoViewModel> friends;

    public FriendsInfoViewModel() {
    }

    private FriendsInfoViewModel(Integer pageNumber, Integer pageSize, Integer totalPages, Long totalElements, List<UserRequestInfoViewModel> friends) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.friends = friends;
    }

    public static FriendsInfoViewModel ofFriends(Page<User> p) {
        return new FriendsInfoViewModel(
                p.getNumber(),
                p.getSize(),
                p.getTotalPages(),
                p.getTotalElements(),
                p.getContent().stream()
                        .map(UserRequestInfoViewModel::fromUser)
                        .collect(Collectors.toList())
        );
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

    public List<UserRequestInfoViewModel> getFriends() {
        return this.friends;
    }
}
