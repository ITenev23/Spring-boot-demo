package bg.paysafe.springboot.api.payload.friendship;

public class FriendshipDeleteRequestModel {

    private Long friendshipId;

    public Long getFriendshipId() {
        return this.friendshipId;
    }

    public void setFriendshipId(Long friendshipId) {
        this.friendshipId = friendshipId;
    }
}
