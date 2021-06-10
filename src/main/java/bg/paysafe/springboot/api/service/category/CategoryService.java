package bg.paysafe.springboot.api.service.category;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.payload.category.CategoryInfoViewModel;
import bg.paysafe.springboot.api.payload.category.CategoryItemInfoViewModel;
import bg.paysafe.springboot.api.payload.category.UserCategoryItemInfoViewModel;
import bg.paysafe.springboot.api.payload.category.UserCategoryItemRequestModel;

import java.util.List;

public interface CategoryService {

    List<CategoryInfoViewModel> getAll();

    List<UserCategoryItemInfoViewModel> getUserCategoryItems(Long id, Long categoryId);

    UserCategoryItemInfoViewModel createUserCategoryItem(User user, UserCategoryItemRequestModel model);

    List<CategoryItemInfoViewModel> getAllCategoryItems(Long id);

}
