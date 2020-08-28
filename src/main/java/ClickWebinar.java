import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class ClickWebinar {

    public static void deleteRecording(int room_Id, int recordingId, String apiKey) {
        try {
            String deleteRecording = "https://api.clickmeeting.com/v1/conferences/" + room_Id +
                    "/recordings/" + recordingId + apiKey;
            URL urlDelete = new URL(deleteRecording);

            HttpURLConnection connection = (HttpURLConnection) urlDelete.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();

            System.out.println("GET Response Code :: " + responseCode);
            System.out.println(responseCode == 200 ? "Successfully deleted video from ClickMeeting." : "Deleting video from ClickMeeting failed");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void downloadToHardDrive(String pathName, Map<Integer,String> recordings, int recordingId){
        try {
            FileUtils.copyURLToFile(
                    new URL(recordings.get(recordingId)),
                    new File(pathName));
            System.out.println("File was successfully downloaded to Hard drive");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int getConferenceId(String address){
        try {
            URL url = new URL(address);
            JSONTokener tokener = new JSONTokener(url.openStream());
            JSONObject object = new JSONObject(tokener);
            JSONArray array = object.getJSONArray("active_conferences");

            JSONObject object2 = array.getJSONObject(0);
            return object2.getInt("id");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static int getRecordingId(Map<Integer,String> recordings){
        return recordings.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .findFirst().get();
    }
}