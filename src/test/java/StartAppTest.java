import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016-04-09.
 */
public class StartAppTest {
    public static void main(String[] args) throws IOException {

        String _url = "http://m232:4567/app/start";
        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "text/html");
        connection.setRequestProperty("Charset", "UTF-8");
        connection.setRequestProperty("user", "zzl");
        connection.setRequestProperty("app", "smoketest.jar");
        connection.setRequestProperty("queue", "default");
        connection.setRequestProperty("input", "/data/input");
        int responseCode = connection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == responseCode) {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
