package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.user.*;
import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.user.*;
import bg.paysafe.springboot.api.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static bg.paysafe.springboot.api.constant.Constants.*;
import static bg.paysafe.springboot.api.constant.URLMappings.*;
import static bg.paysafe.springboot.api.constant.URLMappings.PathVar.FRIEND_ID;
import static bg.paysafe.springboot.api.constant.URLMappings.User.*;

@RestController
@RequestMapping(USER_BASE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = SEARCH_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsersSearchInfoViewModel> search(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "genderId", required = false) Long genderId,
            @RequestParam(value = "genderSearchId", required = false) Long genderSearchId,
            @RequestParam(value = "ageMin", required = false) Integer ageMin,
            @RequestParam(value = "ageMax", required = false) Integer ageMax,
            @RequestParam(value = "cityId", required = false) Long cityId,
            @RequestParam(value = "countryId", required = false) Long countryId,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) {
        return new ResponseEntity<>(this.userService.search(user.getId(), username, genderId, genderSearchId, ageMin, ageMax, cityId, countryId, sort, page, size), HttpStatus.OK);
    }

    @GetMapping(value = NEW)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsersSearchInfoViewModel> newestUsers(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) {
        return new ResponseEntity<>(this.userService.getUsersByRegisterDate(user.getId(), page, size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserInfoViewModel> register(@RequestBody @Valid UserRegisterRequestModel model)
            throws PasswordMismatchException, DuplicateEmailException, DuplicateUsernameException, UserAgeException {
        return new ResponseEntity<>(this.userService.register(model), HttpStatus.CREATED);
    }

    @PostMapping(value = USERNAME)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInfoViewModel> changeUsername(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UsernameRequestModel model
    ) throws UserNotFound, DuplicateUsernameException {
        return new ResponseEntity<>(this.userService.changeUsername(user.getId(), model), HttpStatus.CREATED);
    }

    @PostMapping(value = PASSWORD)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInfoViewModel> changePassword(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserPasswordRequestModel model
    ) throws PasswordMismatchException, UserNotFound {
        return new ResponseEntity<>(this.userService.changePassword(user.getId(), model), HttpStatus.CREATED);
    }

    @GetMapping(value = ME_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileViewModel> profile(@AuthenticationPrincipal User user) throws UserNotFound {
        return ResponseEntity.ok(this.userService.getProfile(user.getId()));
    }

    @GetMapping(value = FRIEND_ID)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserRequestInfoViewModel> profileByUserId(
            @AuthenticationPrincipal User user,
            @PathVariable Long friendId
    ) throws UserNotFound {
        return ResponseEntity.ok(this.userService.getProfileByUserId(user.getId(), friendId));
    }

    @GetMapping(value = USER_ACTIVITY)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserActivityViewModel> userActivities(@PathVariable Long id) throws UserNotFound {
        return ResponseEntity.ok(this.userService.getActivityByUserId(id));
    }

    @GetMapping(value = VALIDATE)
    public ResponseEntity<UsernameValidateInfoModel> validateUsername(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "username") String username
    ) {
        return ResponseEntity.ok(this.userService.validate(email, username));
    }

    @PostMapping(value = PHONE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInfoViewModel> setPhoneNumber(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserPhoneNumberRequestModel model
    ) throws UserNotFound {
        return new ResponseEntity<>(this.userService.setPhoneNumber(user.getId(), model), HttpStatus.CREATED);
    }

    @PostMapping(value = Common.DELETE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessfulRequestViewModel> delete(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.userService.delete(user.getId()));
    }

}
