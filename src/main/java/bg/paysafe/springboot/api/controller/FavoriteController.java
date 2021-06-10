package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.constant.URLMappings;
import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.favorite.FavoriteDeleteRequestModel;
import bg.paysafe.springboot.api.payload.favorite.FavoritesInfoViewModel;
import bg.paysafe.springboot.api.payload.user.UserFavoriteRequestModel;
import bg.paysafe.springboot.api.payload.user.UserFavoritesViewModel;
import bg.paysafe.springboot.api.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static bg.paysafe.springboot.api.constant.Constants.*;
import static bg.paysafe.springboot.api.constant.URLMappings.FAVORITE_BASE;
import static bg.paysafe.springboot.api.constant.URLMappings.ME_BASE;

@RestController
@RequestMapping(FAVORITE_BASE)
public class FavoriteController {

    public final UserService userService;

    public FavoriteController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = ME_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FavoritesInfoViewModel> findAllUserFavorites(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_PROPERTIES_ID) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) {
        return ResponseEntity.ok(this.userService.findAllUserFavorites(user.getId(), sort, page, size));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserFavoritesViewModel> createFavoriteUser(
            @RequestBody @Valid UserFavoriteRequestModel request,
            @AuthenticationPrincipal User user
    ) throws UserNotFound {
        return new ResponseEntity<>(this.userService.createFavoriteUser(user.getId(), request), HttpStatus.CREATED);
    }

    @PostMapping(value = URLMappings.Common.DELETE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessfulRequestViewModel> deleteFavoriteUser(
            @RequestBody @Valid FavoriteDeleteRequestModel request,
            @AuthenticationPrincipal User user
    ) throws UserNotFound {
        return ResponseEntity.ok(this.userService.deleteFavoriteUser(user.getId(), request));
    }

}
