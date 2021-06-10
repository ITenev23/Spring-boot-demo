package bg.paysafe.springboot.api.payload.chat;

import java.util.Date;
import java.util.Set;

public class ConversationRequestModel {

    private Date startedAt;

    private Set<Long> usersId;

    public Date getStartedAt() {
        return this.startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Set<Long> getUsersId() {
        return this.usersId;
    }

    public void setUsersId(Set<Long> usersId) {
        this.usersId = usersId;
    }
}
