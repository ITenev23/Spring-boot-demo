package bg.paysafe.springboot.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friendships_statuses")
public class FriendshipStatus extends Status {
}
