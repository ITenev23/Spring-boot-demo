package bg.paysafe.springboot.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "uploads")
public class Upload extends BaseEntity {

    private String url;

    private String thumbnailUrl;

    private User user;

    private Boolean isVideo;

    private Long typeId;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean getVideo() {
        return isVideo;
    }

    public void setVideo(Boolean video) {
        isVideo = video;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
