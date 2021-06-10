package bg.paysafe.springboot.api.payload.chat;

public class ConversationMessageRequestModel {

    private Long friendId;

    private String message;

    public Long getFriendId() {
        return this.friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
