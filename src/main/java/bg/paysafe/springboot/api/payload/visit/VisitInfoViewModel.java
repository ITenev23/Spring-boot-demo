package bg.paysafe.springboot.api.payload.visit;

import bg.paysafe.springboot.api.entity.UserVisit;

import java.util.Date;

public class VisitInfoViewModel {

    private Long visitorId;

    private Long visitedUserId;

    private Date visitedOn;

    private Long visitCount;

    public VisitInfoViewModel(UserVisit uv) {
        this.visitorId = uv.getUser().getId();
        this.visitedUserId = uv.getVisitedUser().getId();
        this.visitedOn = uv.getVisitedOn();
        this.visitCount = uv.getVisitCount();
    }

    public Long getVisitorId() {
        return this.visitorId;
    }

    public Long getVisitedUserId() {
        return this.visitedUserId;
    }

    public Date getVisitedOn() {
        return this.visitedOn;
    }

    public Long getVisitCount() {
        return this.visitCount;
    }
}
