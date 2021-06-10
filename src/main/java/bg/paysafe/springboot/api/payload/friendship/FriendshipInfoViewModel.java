package bg.paysafe.springboot.api.payload.friendship;

import bg.paysafe.springboot.api.entity.Friendship;

public class FriendshipInfoViewModel {

    private Long id;

    private Long senderId;

    private Long recipientId;

    private FriendshipInfoViewModel(Long id, Long senderId, Long recipientId) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public static FriendshipInfoViewModel ofFriendship(Friendship f) {
        return new FriendshipInfoViewModel(
                f.getId(),
                f.getSender(),
                f.getRecipient()
        );
    }

    public Long getId() {
        return this.id;
    }

    public Long getSenderId() {
        return this.senderId;
    }

    public Long getRecipientId() {
        return this.recipientId;
    }
}
