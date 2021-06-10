package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.user.UserCannotVisitHimselfException;
import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.payload.visit.UserVisitInfoViewModel;
import bg.paysafe.springboot.api.payload.visit.UserVisitedRequestModel;
import bg.paysafe.springboot.api.payload.visit.VisitInfoViewModel;
import bg.paysafe.springboot.api.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static bg.paysafe.springboot.api.constant.Constants.*;
import static bg.paysafe.springboot.api.constant.URLMappings.VISIT_BASE;


@RestController
public class VisitController {

    private final UserService userService;

    public VisitController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = VISIT_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserVisitInfoViewModel> allVisitsByUserId(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "isVisitor", required = false, defaultValue = "true") Boolean isVisitor,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_PROPERTIES_VISITED_ON) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) {
        return ResponseEntity.ok(this.userService.getAllVisitsByUserId(user.getId(), isVisitor, sort, page, size));
    }


    @PostMapping(value = VISIT_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VisitInfoViewModel> addVisit(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserVisitedRequestModel request
    ) throws UserNotFound, UserCannotVisitHimselfException {
        return new ResponseEntity<>(this.userService.createVisit(user.getId(), request), HttpStatus.CREATED);
    }

}
