package bg.paysafe.springboot.api.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class Status extends BaseEntity{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
