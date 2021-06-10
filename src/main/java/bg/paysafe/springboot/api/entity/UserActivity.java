package bg.paysafe.springboot.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "users_activities")
public class UserActivity extends BaseEntity {

    private Long userId;

    private Date lastSeen;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLastSeen() {
        return this.lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

}
