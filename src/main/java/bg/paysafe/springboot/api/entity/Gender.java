package bg.paysafe.springboot.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "genders")
public class Gender extends BaseEntity {

    private String name;

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
