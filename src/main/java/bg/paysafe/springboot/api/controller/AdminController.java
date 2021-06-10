package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.user.*;
import bg.paysafe.springboot.api.service.user.UserService;
import freemarker.template.TemplateException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

import static bg.paysafe.springboot.api.constant.Constants.*;
import static bg.paysafe.springboot.api.constant.URLMappings.*;

@RestController
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = USER_BASE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsersSearchInfoViewModel> getAllUsers(
            @RequestParam(value = "statusId", required = false) Long statusId,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_PROPERTIES_ID) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) {
        return ResponseEntity.ok(this.userService.findAll(statusId, sort, page, size));
    }

    @PostMapping(value = VERIFY_USER)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SuccessfulRequestViewModel> verify(@RequestBody @Valid UserVerifyRequestModel model) throws UserNotFound, TemplateException, IOException, MessagingException {
        return ResponseEntity.ok(this.userService.verify(model));
    }

    @PatchMapping(value = USER_BY_ID)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserInfoViewModel> editUser(
            @PathVariable Long id,
            @RequestBody @Valid AdminUserEditRequestModel model
    ) throws UserNotFound {
        return new ResponseEntity<>(this.userService.editUser(id, model), HttpStatus.CREATED);
    }

    @GetMapping(value = ALL_USER_FRIENDS_BY_ID)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserInfoViewModel> getAllFriendsByUserId(
            @PathVariable Long id
    ) throws UserNotFound {
        return new ResponseEntity<>(this.userService.getAllFriendsByUserId(id), HttpStatus.OK);
    }

    @GetMapping(value = USER_PHONE_NUMBER)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserInfoViewModel> getUserPhoneNumber(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getPhoneNumber(id));
    }

}
