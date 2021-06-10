package bg.paysafe.springboot.api.service.common.io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class DefaultFileService implements FileService {

    private final String staticFileDestination;

    public DefaultFileService(@Value("${static.fileDestination}") String staticFileDestination) {
        this.staticFileDestination = staticFileDestination;
    }

    @Override
    public String upload(String newFilepath, MultipartFile file) throws IOException {
        if (file == null || file.getOriginalFilename() == null) {
            return null;
        }

        var fileNameTokens = file.getOriginalFilename().split("\\.");
        var extension = fileNameTokens[fileNameTokens.length - 1];
        var filePath = newFilepath
                + "."
                + extension;
        file.transferTo(
                Path.of(
                        this.staticFileDestination
                                +
                                filePath
                )
        );
        return filePath;
    }

}
