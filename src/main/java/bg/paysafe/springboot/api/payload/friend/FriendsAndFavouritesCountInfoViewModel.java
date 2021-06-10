package bg.paysafe.springboot.api.payload.friend;

public class FriendsAndFavouritesCountInfoViewModel {

    private Integer friendsCount;

    private Integer favouritesCount;

    public FriendsAndFavouritesCountInfoViewModel(Integer friendsCount, Integer favouritesCount) {
        this.friendsCount = friendsCount;
        this.favouritesCount = favouritesCount;
    }

    public Integer getFriendsCount() {
        return this.friendsCount;
    }

    public Integer getFavouritesCount() {
        return this.favouritesCount;
    }
}
