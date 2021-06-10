package bg.paysafe.springboot.api.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    private String name;

    private Set<User> users;

    public Role() {
        this.users = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    @Transient
    public String getAuthority() {
        return this.name;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

}
