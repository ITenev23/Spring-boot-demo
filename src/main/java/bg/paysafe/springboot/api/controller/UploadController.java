package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.file.PictureNotFoundException;
import bg.paysafe.springboot.api.exception.user.UserNotAuthorized;
import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.payload.file.*;
import bg.paysafe.springboot.api.payload.user.UserInfoViewModel;
import bg.paysafe.springboot.api.payload.user.UserProfileUploadRequestModel;
import bg.paysafe.springboot.api.payload.user.UserUploadsViewModel;
import bg.paysafe.springboot.api.service.upload.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static bg.paysafe.springboot.api.constant.Constants.*;
import static bg.paysafe.springboot.api.constant.URLMappings.*;

@RestController
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping(value = MY_UPLOADS)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserUploadsViewModel> myUploads(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "typeId") Long typeId,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_PROPERTIES_ID) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) {
        return ResponseEntity.ok(this.uploadService.findAllUploadsByUserIdAndTypeId(user.getId(), typeId, sort, page, size));
    }

    @GetMapping(value = UPLOAD_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserUploadsViewModel> getUserUploads(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "typeId") Long typeId,
            @RequestParam(value = "sort", required = false, defaultValue = DEFAULT_SORT_PROPERTIES_ID) String sort,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_SIZE) Integer size
    ) {
        return ResponseEntity.ok(this.uploadService.findAllUploadsByUserIdAndTypeId(userId, typeId, sort, page, size));
    }

    @PostMapping(value = CHANGE_UPLOAD_TYPE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UploadViewModel> changeUploadType(
            @RequestBody @Valid UploadChangeTypeRequestModel request,
            @AuthenticationPrincipal User user
    ) throws UserNotAuthorized {
        return new ResponseEntity<>(
                this.uploadService.changeUploadType(request, user.getId()),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = UPLOAD_FILE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UploadViewModel> uploadFile(
            UploadRequestModel request,
            @AuthenticationPrincipal User user
    ) throws IOException, UserNotFound {
        return new ResponseEntity<>(
                this.uploadService.uploadFile(request, user),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = UPLOAD_CHAT_FILE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UploadPathsViewModel> uploadChatFile(UploadChatFileRequestModel request) throws IOException {
        return new ResponseEntity<>(
                this.uploadService.uploadChatFile(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = SET_PROFILE_IMG)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInfoViewModel> setProfilePicture(
            @RequestBody @Valid UserProfileUploadRequestModel request,
            @AuthenticationPrincipal User user
    ) throws UserNotFound, PictureNotFoundException {
        return new ResponseEntity<>(
                this.uploadService.setProfilePicture(request, user.getId()),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = DELETE_MY_UPLOAD)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UploadViewModel> deleteMyUpload(
            @RequestBody @Valid UserProfileUploadRequestModel request,
            @AuthenticationPrincipal User user
    ) throws UserNotAuthorized {
        return ResponseEntity.ok(this.uploadService.deleteUpload(request, user.getId()));
    }

}
