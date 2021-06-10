package bg.paysafe.springboot.api.payload.user;

import java.util.Date;

public class UserActivityViewModel {

    private Date lastSeen;

    public UserActivityViewModel(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Date getLastSeen() {
        return this.lastSeen;
    }
}
