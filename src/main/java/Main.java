import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        int room_Id = 0;
        String address = "https://api.clickmeeting.com/v1/conferences?api_key=cca200acae29f3b2b3993782b54744f041d77dc1";
        String apiKey = "?api_key=cca200acae29f3b2b3993782b54744f041d77dc1";
        String addressRecordings;
        Map<Integer, String> recordings = new HashMap<>();
        int recordingId = 0;
        String pathName = "ClickMeeting.mp4";

        try {
            room_Id = ClickWebinar.getConferenceId(address);

            addressRecordings = "https://api.clickmeeting.com/v1/conferences/" + room_Id + "/recordings" + apiKey;
            System.out.println(addressRecordings);
            URL url = new URL(addressRecordings);
            JSONTokener tokener = new JSONTokener(url.openStream());
            JSONArray records = new JSONArray(tokener);

            for (int i = 0; i < records.length(); i++) {
                recordings.put(records.getJSONObject(i).getInt("id"), records.getJSONObject(i).getString("recording_url"));
            }

            recordingId = ClickWebinar.getRecordingId(recordings);
            System.out.println(recordingId);
            System.out.println(recordings.get(recordingId));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClickWebinar.downloadToHardDrive(pathName, recordings, recordingId);

        try {
            //implementing of Interface
            UploadService uploadService = new UploadService(new YoutubeUpload());

            if (uploadService.uploadVideo(pathName, "My Video", "My Description", "public")) {

                ClickWebinar.deleteRecording(room_Id, recordingId, apiKey);

                File file = new File(pathName);
                System.out.println(file.delete() ? "File deleted from Hard Drive successfully" : "Failed to delete the file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}