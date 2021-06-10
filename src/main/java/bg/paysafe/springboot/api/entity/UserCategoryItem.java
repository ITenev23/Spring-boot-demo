package bg.paysafe.springboot.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_category_items")
public class UserCategoryItem extends BaseEntity{

    private String value;

    private User user;

    private CategoryItem items;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public CategoryItem getItems() {
        return this.items;
    }

    public void setItems(CategoryItem items) {
        this.items = items;
    }

}
