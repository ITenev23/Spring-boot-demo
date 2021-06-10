package bg.paysafe.springboot.api.payload.user;

public class UserFriendRequestModel {

    Long friendId;

    Long statusId;

    public Long getFriendId() {
        return this.friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Long getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

}
