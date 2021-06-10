package bg.paysafe.springboot.api.payload.chat;

import java.util.Set;

public class SeenMessagesRequestModel {

    private Set<Long> messageIds;

    public Set<Long> getMessageIds() {
        return this.messageIds;
    }

    public void setMessageIds(Set<Long> messageIds) {
        this.messageIds = messageIds;
    }
}
