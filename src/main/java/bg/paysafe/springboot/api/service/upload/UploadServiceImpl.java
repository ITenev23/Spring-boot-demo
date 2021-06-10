package bg.paysafe.springboot.api.service.upload;

import bg.paysafe.springboot.api.entity.Upload;
import bg.paysafe.springboot.api.entity.User;
import bg.paysafe.springboot.api.exception.file.PictureNotFoundException;
import bg.paysafe.springboot.api.exception.user.UserNotAuthorized;
import bg.paysafe.springboot.api.exception.user.UserNotFound;
import bg.paysafe.springboot.api.payload.file.*;
import bg.paysafe.springboot.api.payload.user.UserInfoViewModel;
import bg.paysafe.springboot.api.payload.user.UserProfileUploadRequestModel;
import bg.paysafe.springboot.api.payload.user.UserUploadsViewModel;
import bg.paysafe.springboot.api.repository.UploadRepository;
import bg.paysafe.springboot.api.repository.UserRepository;
import bg.paysafe.springboot.api.service.common.io.PictureService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

import static bg.paysafe.springboot.api.constant.ConfigurationConstants.PictureConfig.*;

@Service
public class UploadServiceImpl implements UploadService {

    private final UserRepository userRepository;

    private final UploadRepository uploadRepository;

    private final PictureService pictureService;

    public UploadServiceImpl(UserRepository userRepository, UploadRepository uploadRepository, PictureService pictureService) {
        this.userRepository = userRepository;
        this.uploadRepository = uploadRepository;
        this.pictureService = pictureService;
    }

    @Override
    public UserUploadsViewModel findAllUploadsByUserIdAndTypeId(Long userId, Long typeId, String sort, Integer page, Integer size) {
        final var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
        return UserUploadsViewModel.ofUserUploads(this.uploadRepository.findAllByUserAndTypeIdWithPaging(userId, typeId, pageable));
    }

    @Override
    public UploadViewModel changeUploadType(UploadChangeTypeRequestModel request, Long id) throws UserNotAuthorized {
        Upload upload = this.uploadRepository.findByIdAndUserId(request.getUploadId(), id)
                .orElseThrow(UserNotAuthorized::new);

        upload.setTypeId(request.getTypeId());

        return UploadViewModel.ofUpload(this.uploadRepository.saveAndFlush(upload));
    }

    @Override
    public UploadViewModel deleteUpload(UserProfileUploadRequestModel request, Long id) throws UserNotAuthorized {
        Upload upload = this.uploadRepository.findByIdAndUserId(request.getUploadId(), id)
                .orElseThrow(UserNotAuthorized::new);

        User user = this.userRepository.findById(id).orElseThrow();
        if (user.getGender().getId().equals(1L)) {
            user.setProfileImg(this.uploadRepository.findById(2L).orElseThrow());
        } else {
            user.setProfileImg(this.uploadRepository.findById(1L).orElseThrow());
        }

        this.userRepository.saveAndFlush(user);

        this.uploadRepository.delete(upload);

        return UploadViewModel.ofUpload(upload);
    }

    @Override
    public UploadViewModel uploadFile(UploadRequestModel request, User user) throws IOException, UserNotFound {
        final var user1 = this.userRepository.findFirstByEmail(user.getEmail())
                .orElseThrow(UserNotFound::new);

        var filePath = PATH_USERS + FULL_PREFIX + new Date().getTime() + "-" + user.getId();
        var images = this.pictureService.uploadImg(filePath, request.getFile());
        
        var upload = new Upload();
        upload.setUrl(images.get(0));
        upload.setThumbnailUrl(images.get(1));
        upload.setTypeId(request.getTypeId());
        upload.setVideo(request.getVideo());
        upload.setUser(user1);

        return UploadViewModel.ofUpload(this.uploadRepository.saveAndFlush(upload));
    }

    @Override
    public UploadPathsViewModel uploadChatFile(UploadChatFileRequestModel request) throws IOException {
        var filePath = PATH_CHAT + FULL_PREFIX + new Date().getTime() + "-" + Math.random();
        var images = this.pictureService.uploadImg(filePath, request.getFile());

        return new UploadPathsViewModel(images.get(0), images.get(1));
    }

    @Override
    public UserInfoViewModel setProfilePicture(UserProfileUploadRequestModel model, Long id) throws UserNotFound, PictureNotFoundException {
        var user = this.userRepository.findById(id)
                .orElseThrow(UserNotFound::new);
        user.setProfileImg(
                this.uploadRepository.findById(model.getUploadId())
                        .orElseThrow(PictureNotFoundException::new)
        );

        return UserInfoViewModel.fromUser(
                this.userRepository.saveAndFlush(user)
        );
    }
}
