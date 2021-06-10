package bg.paysafe.springboot.api.payload.favorite;

public class FavoriteDeleteRequestModel {

    private Long userId;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
