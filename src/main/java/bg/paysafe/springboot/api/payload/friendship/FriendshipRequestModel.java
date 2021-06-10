package bg.paysafe.springboot.api.payload.friendship;

public class FriendshipRequestModel {

    private Long recipientId;

    private Long statusId;

    public Long getRecipientId() {
        return this.recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Long getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

}
