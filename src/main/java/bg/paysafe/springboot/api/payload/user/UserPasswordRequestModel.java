package bg.paysafe.springboot.api.payload.user;

public class UserPasswordRequestModel {

    private String password;

    private String confirm;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return this.confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
