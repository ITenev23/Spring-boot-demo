package bg.paysafe.springboot.api.payload.chat;

import bg.paysafe.springboot.api.entity.User;

public class UserConversationInfoViewModel {

    private Long userId;

    private String username;

    private String profileImg;

    private UserConversationInfoViewModel(Long userId, String username, String thumbnail) {
        this.userId = userId;
        this.username = username;
        this.profileImg = thumbnail;
    }

    public static UserConversationInfoViewModel ofUserConversation(User user) {
        return new UserConversationInfoViewModel(
                user.getId(),
                user.getUsername(),
                user.getProfileImg().getThumbnailUrl()
        );
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getProfileImg() {
        return this.profileImg;
    }

}
