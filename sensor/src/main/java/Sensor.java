import java.io.IOException;
import java.net.URISyntaxException;

public interface Sensor {
    Weather extractData() throws IOException, InterruptedException, URISyntaxException;
}



