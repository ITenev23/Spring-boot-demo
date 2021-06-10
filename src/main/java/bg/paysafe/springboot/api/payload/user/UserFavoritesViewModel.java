package bg.paysafe.springboot.api.payload.user;

public class UserFavoritesViewModel {

    private Long userId;

    private Long favoriteId;

    public UserFavoritesViewModel(Long userId, Long favoriteId) {
        this.userId = userId;
        this.favoriteId = favoriteId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getFavoriteId() {
        return this.favoriteId;
    }
}
