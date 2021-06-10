package bg.paysafe.springboot.api.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    private String name;

    private List<CategoryItem> items;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = CategoryItem.class, mappedBy = "category")
    public List<CategoryItem> getItems() {
        return this.items;
    }

    public void setItems(List<CategoryItem> items) {
        this.items = items;
    }

}
