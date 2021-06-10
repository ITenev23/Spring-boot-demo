package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.friendship.FriendshipNotFound;
import bg.paysafe.springboot.api.exception.user.UserCannotFriendHimselfException;
import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.friend.FriendsAndFavouritesCountInfoViewModel;
import bg.paysafe.springboot.api.payload.friend.FriendsInfoViewModel;
import bg.paysafe.springboot.api.payload.friendship.FriendDeleteRequestModel;
import bg.paysafe.springboot.api.payload.friendship.FriendshipInfoViewModel;
import bg.paysafe.springboot.api.payload.friendship.FriendshipPageViewModel;
import bg.paysafe.springboot.api.payload.user.UserFriendRequestModel;
import bg.paysafe.springboot.api.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static bg.paysafe.springboot.api.constant.Constants.*;
import static bg.paysafe.springboot.api.constant.URLMappings.*;

@RestController
public class FriendController {

    public final UserService userService;

    public FriendController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = MY_FRIENDS)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendsInfoViewModel> findAllUserFriends(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_PROPERTIES_ID) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size) {
        return ResponseEntity.ok(this.userService.findAllUserFriends(user.getId(), sort, page, size));
    }

    @GetMapping(value = FRIENDSHIP_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendshipPageViewModel> findAllUserFriends(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "type") Long type,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_PROPERTIES_ID) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) {
        return ResponseEntity.ok(this.userService.findAllFriendships(user.getId(), type, sort, page, size));
    }

    @GetMapping(value = FRIENDS_AND_FAVOURITES_COUNT)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendsAndFavouritesCountInfoViewModel> countOfAllFriendsAndFavouritesByUserI(@AuthenticationPrincipal User user) throws UserNotFound {
        return ResponseEntity.ok(this.userService.countOfAllFriendsAndFavouritesByUserI(user.getId()));
    }

    @PostMapping(value = FRIENDS_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendshipInfoViewModel> createFriendshipRequest(
            @RequestBody @Valid UserFriendRequestModel request,
            @AuthenticationPrincipal User user
    ) throws UserNotFound, UserCannotFriendHimselfException {
        return new ResponseEntity<>(
                this.userService.createFriendshipRequest(request, user.getId()),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = DELETE_FRIEND)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessfulRequestViewModel> deleteFriend(
            @RequestBody @Valid FriendDeleteRequestModel request,
            @AuthenticationPrincipal User user
    ) throws UserNotFound {
        return ResponseEntity.ok(this.userService.deleteFriend(user.getId(), request));
    }

    @PostMapping(value = UPDATE_FRIENDSHIP_STATUS)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FriendshipInfoViewModel> editFriendshipStatus(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserFriendRequestModel request
    ) throws FriendshipNotFound, UserNotFound {
        return new ResponseEntity<>(
                this.userService.editFriendshipStatus(request, user.getId()),
                HttpStatus.CREATED
        );
    }

}
