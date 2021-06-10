package bg.paysafe.springboot.api.service.common.io;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String upload(String newFilepath, MultipartFile file) throws IOException;

}
