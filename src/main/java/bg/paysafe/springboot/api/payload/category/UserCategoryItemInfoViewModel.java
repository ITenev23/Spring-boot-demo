package bg.paysafe.springboot.api.payload.category;

import bg.paysafe.springboot.api.entity.UserCategoryItem;

public class UserCategoryItemInfoViewModel {

    private Long id;

    private CategoryItemInfoViewModel categoryItem;

    private String value;

    private UserCategoryItemInfoViewModel(Long id, CategoryItemInfoViewModel categoryItem, String value) {
        this.id = id;
        this.categoryItem = categoryItem;
        this.value = value;
    }

    public static UserCategoryItemInfoViewModel ofUserCategoryItem(UserCategoryItem userCategoryItem) {
        return new UserCategoryItemInfoViewModel(
                userCategoryItem.getId(),
                CategoryItemInfoViewModel.ofCategoryItem(userCategoryItem.getItems()),
                userCategoryItem.getValue()
        );
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryItemInfoViewModel getCategoryItem() {
        return this.categoryItem;
    }

    public void setCategoryItem(CategoryItemInfoViewModel categoryItem) {
        this.categoryItem = categoryItem;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
