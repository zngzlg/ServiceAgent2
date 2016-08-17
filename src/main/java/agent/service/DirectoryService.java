package agent.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016-08-15.
 */
public class DirectoryService {
    public boolean overwrite(String path) throws IOException {
        File file = new File(path);
        if (file.exists())
            Files.delete(Paths.get(file.toURI()));
        else {
            File parent = new File(file.getParent());
            if (!parent.exists())
                parent.mkdirs();
            file.createNewFile();
        }
        return true;
    }
}
