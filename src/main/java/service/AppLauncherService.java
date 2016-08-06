package service;

import spark.Request;

import javax.servlet.ServletInputStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by Administrator on 2016-04-07.
 */
public class AppLauncherService {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "<p>Hello World,this<br> is<br> a<br> test<br> message<br></p>");
        get("/resource", (req, res) -> "resource config");
        get("/app/smoke", (request, response) -> SmokeTest());
        get("/app/start", (req, res) -> StartApp(req));
        post("/app/upload", (request, response) -> PostApp(request));
        post("/data/upload", (request, response) -> PostData(request));
    }

    private static void checkExists(File jar) throws IOException {
        if (jar.exists())
            Files.delete(Paths.get(jar.toURI()));
        else {
            File path = new File(jar.getParent());
            if (!path.exists())
                path.mkdirs();
            jar.createNewFile();
        }
    }

    private static void writeData(ServletInputStream input, FileOutputStream output, int length) throws IOException {
        int bytes = 0;
        byte[] bucket = new byte[length];
        if (input.isReady()) {
            bytes = input.read(bucket);
            if (bytes == length)
                output.write(bucket);
        }

    }

    private static String PostApp(Request request) {
        try {
            String user = request.headers("user");
            String app = request.headers("app");
            File jar = new File("/data/app-data/user/" + user + "/app/" + app);
            checkExists(jar);
            ServletInputStream input = request.raw().getInputStream();
            int length = request.contentLength();
            FileOutputStream output = new FileOutputStream(jar);
            writeData(input, output, length);
            output.close();
            input.close();
            return "Upload success: " + jar.getPath();
        } catch (IOException exception) {
            return exception.getMessage();
        }

    }

    private static String PostData(Request request) throws IOException {
        String user = request.headers("user");
        String input = request.headers("input");
        String fileName = request.headers("file");
        try {
            File data = new File("/data/app-data/user/" + user + "/data/" + fileName);
            checkExists(data);
            ServletInputStream stream = request.raw().getInputStream();
            int length = request.contentLength();
            FileOutputStream output = new FileOutputStream(data);
            writeData(stream, output,length);
            output.close();
            stream.close();

            String filePath = getDataPath(user, fileName);
            Process proc = Runtime
                    .getRuntime()
                    .exec(String.format("python /data/uploadData.py %s %s %s",
                            user,
                            input,
                            filePath));

            InputStream inStream = proc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            StringBuffer jobID = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                jobID.append(line + "<br>");
            }
            return "<p>" + new String(jobID) + "</p>";

        } catch (IOException exception) {
            return exception.getMessage();
        }
    }

    private static String StartApp(Request request) throws IOException {
        String user = request.headers("user");
        String app = request.headers("app");
        String input = request.headers("input");
        String queue = request.headers("queue");
        String time = request.headers("startTime");
        String app_path = String.format(getAppDirectory(user) + app);

        Process proc = Runtime
                .getRuntime()
                .exec(String.format("python /data/startApp.py %s %s %s %s %s",
                        user,
                        app_path,
                        input,
                        queue,
                        time));

        InputStream inStream = proc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        StringBuffer jobID = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            jobID.append(line + "<br>");
        }
        return "<p>" + new String(jobID) + "</p>";
    }

    private static String getAppDirectory(String user) {
        return "/data/app-data/user/" + user + "/app/";
    }

    private static String getDataPath(String user, String fileName) {
        return "/data/app-data/user/" + user + "/data/" + fileName;
    }

    private static String SmokeTest() throws IOException {
        Process proc = Runtime.getRuntime().exec("python /data/helloworld.py");
        InputStream inStream = proc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        StringBuffer jobID = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            jobID.append(line + "<br>");
        }
        return "<p>" + new String(jobID) + "</p>";
    }

}
