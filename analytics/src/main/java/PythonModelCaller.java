import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonModelCaller {

    void callPython(String directoryBD) throws IOException {
        createCSV(directoryBD);
        Process process = createProcess();
        showPythonPrints(process);
    }

    private void createCSV(String directoryBD) throws IOException {
        CSVFileWriter csvWriter = new CSVFileWriter();
        WeatherDB weatherDB = new WeatherDB(directoryBD);
        csvWriter.csvFileWriter(weatherDB.selectAll());
    }

    private Process createProcess() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("python", "main.py" );
        processBuilder.directory(new File(System.getProperty("user.dir") + "\\..\\analytics\\predictive_model\\"));
        Process process  = processBuilder.start();
        return process;
    }

    private void showPythonPrints(Process process) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        System.out.println("Running Python starts...: ");
        while ((line = buffer.readLine()) != null){
            System.out.println("Python Output: " + line);
        }
        System.out.println("Running Python ended");
    }
}
