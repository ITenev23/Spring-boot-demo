package bg.paysafe.springboot.api.payload.user;

public class AdminUserEditRequestModel {

    private String username;

    private Boolean isDeleted;


    public AdminUserEditRequestModel(String username, Boolean isDeleted) {
        this.username = username;
        this.isDeleted = isDeleted;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean isDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

}
