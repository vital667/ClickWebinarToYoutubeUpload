import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class YoutubeUpload implements Uploadable {
    @Override
    public boolean uploadVideo(String pathName, String title, String description, String privacyStatus) throws Exception {

        try {
            YouTube youtubeService = YoutubeServices.getService();
            // Define the Video object, which will be uploaded as the request body.
            Video video = new Video();

            // Add the snippet object property to the Video object.
            VideoSnippet snippet = new VideoSnippet();
            snippet.setCategoryId("22");
            snippet.setDescription(description);
            snippet.setTitle(title);
            video.setSnippet(snippet);

            // Add the status object property to the Video object.
            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus(privacyStatus);
            video.setStatus(status);

            File mediaFile = new File(pathName);
            InputStreamContent mediaContent = new InputStreamContent("application/octet-stream", new BufferedInputStream(new FileInputStream(mediaFile)));
            mediaContent.setLength(mediaFile.length());

            // Define and execute the API request
            YouTube.Videos.Insert request = youtubeService.videos().insert("snippet,status", video, mediaContent);
            Video response = request.execute();

            System.out.println("Success! Video was Uploaded to Youtube");
            return true;
        } catch (JSONException e) {
            System.out.println("An error occurred, uploading to Youtube failed");
            return false;
        }
    }
}