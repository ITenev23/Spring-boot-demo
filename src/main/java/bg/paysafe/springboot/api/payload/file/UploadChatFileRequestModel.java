package bg.paysafe.springboot.api.payload.file;

import org.springframework.web.multipart.MultipartFile;

public class UploadChatFileRequestModel {

    private MultipartFile file;

    public MultipartFile getFile() {
        return this.file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
