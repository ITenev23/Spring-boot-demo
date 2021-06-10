package bg.paysafe.springboot.api.payload.user;

public class UserPhoneNumberRequestModel {

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
