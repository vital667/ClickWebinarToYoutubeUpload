public class UploadService {
    private Uploadable uploadable;

    public UploadService(Uploadable uploadable) {
        this.uploadable = uploadable;
    }

    public void setUploadable(Uploadable uploadable) {
        this.uploadable = uploadable;
    }

    boolean uploadVideo(String pathName, String title, String description, String privacyStatus) throws Exception {
        return uploadable.uploadVideo(pathName,title,description,privacyStatus);
    }
}