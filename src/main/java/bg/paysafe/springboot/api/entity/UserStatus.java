package bg.paysafe.springboot.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users_statuses")
public class UserStatus extends Status {
}
