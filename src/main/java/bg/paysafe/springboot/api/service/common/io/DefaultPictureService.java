package bg.paysafe.springboot.api.service.common.io;

import bg.paysafe.springboot.api.entity.Upload;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static bg.paysafe.springboot.api.constant.ConfigurationConstants.PictureConfig.*;

@Service
public class DefaultPictureService implements PictureService {

    private final String staticFileDestination;

    public DefaultPictureService(@Value("${static.fileDestination}") String staticFileDestination) {
        this.staticFileDestination = staticFileDestination;
    }

    @Override
    public List<String> uploadImg(String newFilepath, MultipartFile file) throws IOException {
        if (file == null ||
                file.getOriginalFilename() == null ||
                file.getSize() > MAX_FILE_SIZE ||
                Arrays.stream(ALLOWED_FILE_FORMATS)
                        .noneMatch(type -> type.equals(file.getContentType()))
        ) {
            return null;
        }

        var fileNameTokens = file.getOriginalFilename().split("\\.");
        final var extension = fileNameTokens[fileNameTokens.length - 1];
        var thumbnailPath = newFilepath.replace(FULL_PREFIX, THUMBNAIL_PREFIX).concat(".").concat(extension);
        newFilepath = newFilepath.concat(".").concat(extension);
        System.out.println(newFilepath);
        this.uploadImages(ImageIO.read(file.getInputStream()), newFilepath, thumbnailPath);

        return List.of(newFilepath, thumbnailPath);
    }

    private void uploadImages(BufferedImage image, String newFilePath, String thumbnailPath) throws IOException {
        final var originalImageFile = new File(this.staticFileDestination + newFilePath);
        final var thumbnailImageFile = new File(this.staticFileDestination + thumbnailPath);
        if (image.getWidth() > THUMBNAIL_WIDTH || image.getHeight() > THUMBNAIL_HEIGHT)
            Thumbnails.of(image).size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT).toFile(thumbnailImageFile);
        else
            Thumbnails.of(image).size(image.getWidth(), image.getHeight()).toFile(thumbnailImageFile);

        if (image.getWidth() > FULL_HD_WIDTH || image.getHeight() > FULL_HD_HEIGHT)
            Thumbnails.of(image).size(FULL_HD_WIDTH, FULL_HD_HEIGHT).toFile(originalImageFile);
        else
            Thumbnails.of(image).size(image.getWidth(), image.getHeight()).toFile(originalImageFile);
    }

    @Override
    public Boolean deleteAll(Collection<Upload> userImgs) {
        if (userImgs == null || userImgs.isEmpty()) return false;

        userImgs.forEach(u -> this.delete(u.getUrl(), u.getThumbnailUrl()));

        return true;
    }

    @Override
    public Boolean deleteImg(String full, String thumbnail) {
        return this.delete(full, thumbnail);
    }

    private Boolean delete(String full, String thumbnail) {
        if (full == null || thumbnail == null) return false;

        File file = new File(this.staticFileDestination + full);
        File fileThumbnail = new File(this.staticFileDestination + thumbnail);

        if (file.exists()) file.delete();
        if (fileThumbnail.exists()) fileThumbnail.delete();

        return true;
    }

}
