package bg.paysafe.springboot.api.payload.friendship;

public class FriendshipStatusRequestModel {

    private Long friendshipId;

    private Long statusId;

    public Long getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getFriendshipId() {
        return this.friendshipId;
    }

    public void setFriendshipId(Long friendshipId) {
        this.friendshipId = friendshipId;
    }
}
