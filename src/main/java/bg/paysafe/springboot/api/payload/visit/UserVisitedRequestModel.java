package bg.paysafe.springboot.api.payload.visit;

public class UserVisitedRequestModel {

    private Long visitedUserId;

    public Long getVisitedUserId() {
        return this.visitedUserId;
    }

    public void setVisitedUserId(Long visitedUserId) {
        this.visitedUserId = visitedUserId;
    }

}
