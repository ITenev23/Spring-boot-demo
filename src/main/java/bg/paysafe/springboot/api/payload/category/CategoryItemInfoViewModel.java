package bg.paysafe.springboot.api.payload.category;

import bg.paysafe.springboot.api.entity.CategoryItem;

public class CategoryItemInfoViewModel {

    private Long id;

    private String label;

    public CategoryItemInfoViewModel(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public static CategoryItemInfoViewModel ofCategoryItem(CategoryItem item) {
        return new CategoryItemInfoViewModel(item.getId(), item.getLabel());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
