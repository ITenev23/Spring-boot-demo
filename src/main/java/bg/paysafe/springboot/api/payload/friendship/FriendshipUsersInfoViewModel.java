package bg.paysafe.springboot.api.payload.friendship;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.payload.user.UserRequestInfoViewModel;

public class FriendshipUsersInfoViewModel {

    private Long id;

    private String status;

    private UserRequestInfoViewModel sender;

    private UserRequestInfoViewModel recipient;

    public static FriendshipUsersInfoViewModel ofFriendship(Long id, String status, User sender, User recipient) {
        var model = new FriendshipUsersInfoViewModel();

        model.setId(id);
        model.setStatus(status);
        model.setRecipient(UserRequestInfoViewModel.fromUser(recipient));
        model.setSender(UserRequestInfoViewModel.fromUser(sender));

        return model;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserRequestInfoViewModel getSender() {
        return this.sender;
    }

    public void setSender(UserRequestInfoViewModel sender) {
        this.sender = sender;
    }

    public UserRequestInfoViewModel getRecipient() {
        return this.recipient;
    }

    public void setRecipient(UserRequestInfoViewModel recipient) {
        this.recipient = recipient;
    }

}
