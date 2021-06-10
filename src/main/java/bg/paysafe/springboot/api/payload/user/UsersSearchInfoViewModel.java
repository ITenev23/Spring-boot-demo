package bg.paysafe.springboot.api.payload.user;

import bg.paysafe.springboot.api.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersSearchInfoViewModel {

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages;

    private Long totalElements;

    private List<UserRequestInfoViewModel> users;

    public static UsersSearchInfoViewModel ofUsersSearch(Page<User> p) {
        var model = new UsersSearchInfoViewModel();

        model.setPageNumber(p.getNumber());
        model.setPageSize(p.getSize());
        model.setTotalPages(p.getTotalPages());
        model.setTotalElements(p.getTotalElements());
        model.setUsers(p.getContent().stream().map(UserRequestInfoViewModel::fromUser).collect(Collectors.toList()));

        return model;
    }

    public static UsersSearchInfoViewModel ofUsers(User p) {
        var model = new UsersSearchInfoViewModel();
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

    public List<UserRequestInfoViewModel> getUsers() {
        return this.users;
    }

    public void setUsers(List<UserRequestInfoViewModel> users) {
        this.users = users;
    }
}
