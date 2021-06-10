package bg.paysafe.springboot.api.payload.file;

public class UploadChangeTypeRequestModel {

    private Long uploadId;
    private Long typeId;

    public Long getUploadId() {
        return uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

}
