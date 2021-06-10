package bg.paysafe.springboot.api.payload.user;

import bg.paysafe.springboot.api.entity.Upload;

public class UserImagesViewModel {

    private Long id;

    private String fullUrl;

    private String thumbnailUrl;

    private Long typeId;

    private UserImagesViewModel(Long id, String fullUrl, String thumbnailUrl, Long typeId) {
        this.id = id;
        this.fullUrl = fullUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.typeId = typeId;
    }

    public static UserImagesViewModel ofUserImages(Upload upload) {
        return new UserImagesViewModel(
                upload.getId(),
                upload.getUrl(),
                upload.getThumbnailUrl(),
                upload.getTypeId()
        );
    }

    public Long getId() {
        return this.id;
    }

    public String getFullUrl() {
        return this.fullUrl;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public Long getTypeId() {
        return this.typeId;
    }

}
