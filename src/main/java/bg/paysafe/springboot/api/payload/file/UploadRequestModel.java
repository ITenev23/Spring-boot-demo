package bg.paysafe.springboot.api.payload.file;

import org.springframework.web.multipart.MultipartFile;

public class UploadRequestModel {

    private MultipartFile file;

    private Long typeId;

    private Boolean isVideo;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Boolean getVideo() {
        return isVideo;
    }

    public void setVideo(Boolean video) {
        isVideo = video;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
