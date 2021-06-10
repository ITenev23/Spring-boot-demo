package bg.paysafe.springboot.api.payload.user;

public class UsernameValidateInfoModel {

    private Boolean email;

    private Boolean username;

    public UsernameValidateInfoModel(Boolean email, Boolean username) {
        this.email = email;
        this.username = username;
    }

    public Boolean isEmail() {
        return this.email;
    }

    public Boolean isUsername() {
        return this.username;
    }

}
