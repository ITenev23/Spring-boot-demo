package bg.paysafe.springboot.api.service.category;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.entity.UserCategoryItem;
import bg.paysafe.springboot.api.payload.category.CategoryInfoViewModel;
import bg.paysafe.springboot.api.payload.category.CategoryItemInfoViewModel;
import bg.paysafe.springboot.api.payload.category.UserCategoryItemInfoViewModel;
import bg.paysafe.springboot.api.payload.category.UserCategoryItemRequestModel;
import bg.paysafe.springboot.api.repository.CategoryItemRepository;
import bg.paysafe.springboot.api.repository.CategoryRepository;
import bg.paysafe.springboot.api.repository.UserCategoryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryItemRepository categoryItemRepository;

    private final UserCategoryItemRepository userCategoryItemRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryItemRepository categoryItemRepository, UserCategoryItemRepository userCategoryItemRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryItemRepository = categoryItemRepository;
        this.userCategoryItemRepository = userCategoryItemRepository;
    }

    @Override
    public List<CategoryInfoViewModel> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryInfoViewModel::ofCategory)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserCategoryItemInfoViewModel> getUserCategoryItems(Long userId, Long categoryId) {
        return this.userCategoryItemRepository
                .findAllByUserIdAndCategoryId(userId, categoryId).stream()
                .map(UserCategoryItemInfoViewModel::ofUserCategoryItem)
                .collect(Collectors.toList());
    }

    @Override
    public UserCategoryItemInfoViewModel createUserCategoryItem(User user, UserCategoryItemRequestModel model) {
        var userItem = this.userCategoryItemRepository.findByUserIdAndItemsId(user.getId(), model.getCategoryItemId());

        if (userItem == null) {
            userItem = new UserCategoryItem();
            userItem.setUser(user);
            userItem.setItems(this.categoryItemRepository.findById(model.getCategoryItemId()).get());
        }
        userItem.setValue(model.getValue());

        return UserCategoryItemInfoViewModel
                .ofUserCategoryItem(this.userCategoryItemRepository.saveAndFlush(userItem));
    }

    @Override
    public List<CategoryItemInfoViewModel> getAllCategoryItems(Long id) {
        return this.categoryItemRepository.findAllByCategoryId(id).stream()
                .map(CategoryItemInfoViewModel::ofCategoryItem)
                .collect(Collectors.toList());
    }

}
