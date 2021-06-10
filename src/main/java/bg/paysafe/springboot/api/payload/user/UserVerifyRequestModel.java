package bg.paysafe.springboot.api.payload.user;

public class UserVerifyRequestModel {

    private Long userId;

    private String message;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
