package bg.paysafe.springboot.api.payload.friendship;

public class FriendDeleteRequestModel {

    private Long friendId;

    public Long getFriendId() {
        return this.friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

}
