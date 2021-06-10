package bg.paysafe.springboot.api.payload.file;

public class UploadPathsViewModel {

    private String thumbnail;

    private String full;

    public UploadPathsViewModel(String thumbnail, String full) {
        this.thumbnail = thumbnail;
        this.full = full;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public String getFull() {
        return this.full;
    }

}
