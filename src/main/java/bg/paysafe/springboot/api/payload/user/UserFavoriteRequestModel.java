package bg.paysafe.springboot.api.payload.user;

public class UserFavoriteRequestModel {

    private Long userId;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
