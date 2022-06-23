import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class JsonFileWritter {

    private String message;
    private String directory;

    public JsonFileWritter(String message, String directory) {
        this.message = message;
        this.directory = directory;
    }

    public void writeMessage() throws IOException {
        FileWriter file = createOpenFile(getFileName());
        file.write(this.message + "\n");
        closeFile(file);
    }

    private String getFileName() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHH");
        JsonObject jsonObject = new Gson().fromJson(this.message, JsonObject.class);
        String ts = df.format(Date.from(
                    Instant.parse(
                        jsonObject.get("ts").getAsString())));
        return ts;
    }

    public FileWriter createOpenFile(String dt) throws IOException {
        String path = checkFilePath();
        return new FileWriter(path + "\\" + dt +".json", true);

    }

    public String checkFilePath(){

        String stringPath = "datalake\\events\\sensor.Weather";
        try {
            Path path = Paths.get(this.directory + "\\" + stringPath);
            Files.createDirectories(path);
            return path.toFile().getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
            return this.directory + "\\" +stringPath;
        }
    }

    public static void closeFile(FileWriter file){
        try {
            if (null != file){
                file.close();
            }

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}


