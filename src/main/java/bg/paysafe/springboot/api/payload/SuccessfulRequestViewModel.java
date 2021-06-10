package bg.paysafe.springboot.api.payload;

public class SuccessfulRequestViewModel {

    private Boolean isSuccessful;

    public SuccessfulRequestViewModel(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public Boolean isSuccessful() {
        return this.isSuccessful;
    }

}
