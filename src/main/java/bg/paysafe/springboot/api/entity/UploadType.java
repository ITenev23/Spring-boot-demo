package bg.paysafe.springboot.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "upload_types")
public class UploadType extends BaseEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
