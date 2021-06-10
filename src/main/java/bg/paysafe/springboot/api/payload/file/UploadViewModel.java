package bg.paysafe.springboot.api.payload.file;

import bg.paysafe.springboot.api.entity.Upload;

import java.util.*;

public class UploadViewModel {

    private Long id;

    private Long typeId;

    private String url;

    private String thumbnailUrl;

    private UploadViewModel(Long id, Long typeId, String url, String thumbnailUrl) {
        this.id = id;
        this.typeId = typeId;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static UploadViewModel ofUpload(Upload u) {
        return new UploadViewModel(
                u.getId(),
                u.getTypeId(),
                u.getUrl(),
                u.getThumbnailUrl()
        );
    }

    public Long getId() {
        return this.id;
    }

    public Long getTypeId() {
        return this.typeId;
    }

    public String getUrl() {
        return this.url;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

}
