package bg.paysafe.springboot.api.service.upload;

import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.file.PictureNotFoundException;
import bg.paysafe.springboot.api.exception.user.UserNotAuthorized;
import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.payload.file.*;
import bg.paysafe.springboot.api.payload.user.UserInfoViewModel;
import bg.paysafe.springboot.api.payload.user.UserProfileUploadRequestModel;
import bg.paysafe.springboot.api.payload.user.UserUploadsViewModel;

import java.io.IOException;

public interface UploadService {

    UploadViewModel uploadFile(UploadRequestModel request, User user) throws IOException, UserNotFound;

    UploadPathsViewModel uploadChatFile(UploadChatFileRequestModel request) throws IOException;

    UserInfoViewModel setProfilePicture(UserProfileUploadRequestModel request, Long id) throws UserNotFound, PictureNotFoundException;

    UserUploadsViewModel findAllUploadsByUserIdAndTypeId(Long userId, Long typeId, String sort, Integer page, Integer size);

    UploadViewModel changeUploadType(UploadChangeTypeRequestModel request, Long id) throws UserNotAuthorized;

    UploadViewModel deleteUpload(UserProfileUploadRequestModel request, Long id) throws UserNotAuthorized;

}
