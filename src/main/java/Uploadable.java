public interface Uploadable {
    boolean uploadVideo(String pathName, String title, String description, String privacyStatus) throws Exception;
}