package bg.paysafe.springboot.api.payload.category;

import bg.paysafe.springboot.api.entity.Category;

public class CategoryInfoViewModel {

    private Long id;

    private String name;

    private CategoryInfoViewModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CategoryInfoViewModel ofCategory(Category category) {
        return new CategoryInfoViewModel(
                category.getId(),
                category.getName()
        );
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
