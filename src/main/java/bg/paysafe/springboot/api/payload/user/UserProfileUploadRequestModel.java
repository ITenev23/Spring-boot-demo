package bg.paysafe.springboot.api.payload.user;

public class UserProfileUploadRequestModel {

    private Long uploadId;

    public Long getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

}
