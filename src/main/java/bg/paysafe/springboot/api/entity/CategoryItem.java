package bg.paysafe.springboot.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "category_items")
public class CategoryItem extends BaseEntity {

    private String label;

    private Category category;

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @ManyToOne(targetEntity = Category.class)
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
