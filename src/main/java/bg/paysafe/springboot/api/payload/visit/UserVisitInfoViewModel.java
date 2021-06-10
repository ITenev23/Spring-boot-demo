package bg.paysafe.springboot.api.payload.visit;

import bg.paysafe.springboot.api.entity.UserVisit;
import bg.paysafe.springboot.api.payload.user.UserRequestInfoViewModel;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.stream.Collectors;

public class UserVisitInfoViewModel {

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages;

    private Long totalElements;

    private Set<UserRequestInfoViewModel> users;

    public static UserVisitInfoViewModel ofVisits(Page<UserVisit> p, Boolean isVisitor) {
        var model = new UserVisitInfoViewModel();

        model.setPageNumber(p.getNumber());
        model.setPageSize(p.getSize());
        model.setTotalPages(p.getTotalPages());
        model.setTotalElements(p.getTotalElements());
        if (isVisitor)
            model.setUsers(p.getContent().stream().map(u -> UserRequestInfoViewModel.fromUser(u.getVisitedUser())).collect(Collectors.toSet()));
        else
            model.setUsers(p.getContent().stream().map(u -> UserRequestInfoViewModel.ofUserVisits(u.getUser(), u.getVisitCount())).collect(Collectors.toSet()));

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

    public Set<UserRequestInfoViewModel> getUsers() {
        return this.users;
    }

    public void setUsers(Set<UserRequestInfoViewModel> users) {
        this.users = users;
    }
}
