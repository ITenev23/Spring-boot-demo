package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.payload.category.CategoryInfoViewModel;
import bg.paysafe.springboot.api.payload.category.CategoryItemInfoViewModel;
import bg.paysafe.springboot.api.payload.category.UserCategoryItemInfoViewModel;
import bg.paysafe.springboot.api.payload.category.UserCategoryItemRequestModel;
import bg.paysafe.springboot.api.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static bg.paysafe.springboot.api.constant.URLMappings.*;

@RestController
public class CategoryController {

    private final CategoryService categoryRepository;

    public CategoryController(CategoryService categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(value = CATEGORY_BASE)
    public ResponseEntity<List<CategoryInfoViewModel>> getAllCategories() {
        return new ResponseEntity<>(this.categoryRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = CATEGORY_ITEMS_BY_ID)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CategoryItemInfoViewModel>> getAllCategoryItems(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(
                this.categoryRepository.getAllCategoryItems(id), HttpStatus.OK);
    }

    @GetMapping(value = MY_CATEGORY_ITEMS_BY_ID)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserCategoryItemInfoViewModel>> getUserCategoryItems(
            @AuthenticationPrincipal User user,
            @PathVariable Long categoryId
    ) {
        return new ResponseEntity<>(this.categoryRepository.getUserCategoryItems(user.getId(), categoryId), HttpStatus.OK);
    }

    @GetMapping(value = USER_CATEGORY_ITEMS_BY_ID)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserCategoryItemInfoViewModel>> getUserCategoryItems(
            @PathVariable Long userId,
            @PathVariable Long categoryId
    ) {
        return new ResponseEntity<>(this.categoryRepository.getUserCategoryItems(userId, categoryId), HttpStatus.OK);
    }

    @PostMapping(value = CREATE_CATEGORY_ITEM)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserCategoryItemInfoViewModel> createUserCategoryItems(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserCategoryItemRequestModel model
    ) {
        return new ResponseEntity<>(this.categoryRepository.createUserCategoryItem(user, model), HttpStatus.OK);
    }


}
