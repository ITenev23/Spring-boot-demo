package bg.paysafe.springboot.api.payload.user;

import bg.paysafe.springboot.api.entity.Upload;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UserUploadsViewModel {

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPages;

    private Long totalElements;

    private List<UserImagesViewModel> uploads;

    public static UserUploadsViewModel ofUserUploads(Page<Upload> u){
        var model = new UserUploadsViewModel();
        model.setPageNumber(u.getNumber());
        model.setPageSize(u.getSize());
        model.setTotalPages(u.getTotalPages());
        model.setTotalElements(u.getTotalElements());
        model.setUploads(u.getContent().stream().map(UserImagesViewModel::ofUserImages).collect(Collectors.toList()));

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

    public List<UserImagesViewModel> getUploads() {
        return this.uploads;
    }

    public void setUploads(List<UserImagesViewModel> uploads) {
        this.uploads = uploads;
    }
}
