import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVFileWriter {

    public void csvFileWriter(ArrayList<Weather> rows) throws IOException {
        FileWriter fileWriter = createOpenCsv();
        try {
            PrintWriter Writer= new PrintWriter(fileWriter);
            for (int i = 0; i < rows.size(); i++) {
                Writer.append(rows.get(i).getTs().toEpochMilli() + ";" + rows.get(i).getTemp() + "\n");
            }
            closeFile(fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileWriter createOpenCsv() throws IOException {

        File file = new File(System.getProperty("user.dir") + "\\..\\analytics\\predictive_model\\Weather.csv");
        if (file.exists()) {
            return new FileWriter(System.getProperty("user.dir") + "\\..\\analytics\\predictive_model\\Weather.csv", true);
        } else {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "\\..\\analytics\\predictive_model\\Weather.csv");
            PrintWriter Writer = new PrintWriter(fileWriter);
            Writer.append("ts" + ";" + "temperature" + "\n");
            return fileWriter;
        }
    }

    public void closeFile(FileWriter file) {
        try {
            if (null != file)
                file.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
