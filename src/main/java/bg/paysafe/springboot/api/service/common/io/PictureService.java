package bg.paysafe.springboot.api.service.common.io;

import bg.paysafe.springboot.api.entity.Upload;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface PictureService {

    List<String> uploadImg(String newFilepath, MultipartFile file) throws IOException;

    Boolean deleteAll(Collection<Upload> userImgs);

    Boolean deleteImg(String full, String thumbnail);

}
