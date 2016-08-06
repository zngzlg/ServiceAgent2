import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016-04-09.
 */
public class PostAppTest {
    public static void main(String[] args) throws IOException {

        String _url = "http://222.201.145.144:4567/data/upload";
        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        byte[] bytes = Files.readAllBytes(Paths.get("D:\\Download\\1.gz"));

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setRequestProperty("Content-Length",String.valueOf(bytes.length));
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Charset", "UTF-8");
        connection.setRequestProperty("user", "zhangzl");
        connection.setRequestProperty("input","/input/fottest/");
        connection.setRequestProperty("file", "fottest3.jar");
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(bytes);
        outputStream.close();
        int responseCode = connection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == responseCode) {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        else
        {
            System.out.println("post err");
        }
    }
}
