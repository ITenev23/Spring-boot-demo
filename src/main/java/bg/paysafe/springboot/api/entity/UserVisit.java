package bg.paysafe.springboot.api.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "users_visits")
public class UserVisit extends BaseEntity {

    private User user;

    private User visitedUser;

    private Date visitedOn;

    private Long visitCount;

    public UserVisit() {
        this.visitCount = 0L;
    }

    @ManyToOne
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public User getVisitedUser() {
        return this.visitedUser;
    }

    public void setVisitedUser(User visitedUser) {
        this.visitedUser = visitedUser;
    }

    public Date getVisitedOn() {
        return this.visitedOn;
    }

    public void setVisitedOn(Date visitedOn) {
        this.visitedOn = visitedOn;
    }

    public Long getVisitCount() {
        return this.visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    @Transient
    public void incrementVisitCount() {
        this.visitCount++;
    }

}
