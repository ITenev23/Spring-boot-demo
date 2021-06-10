package bg.paysafe.springboot.api.payload.category;

public class UserCategoryItemRequestModel {

    private String value;

    private Long categoryItemId;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCategoryItemId() {
        return this.categoryItemId;
    }

    public void setCategoryItemId(Long categoryItemId) {
        this.categoryItemId = categoryItemId;
    }

}
