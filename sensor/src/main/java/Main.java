import org.apache.log4j.BasicConfigurator;
import javax.jms.JMSException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class Main{

    public static void main(String[] args) throws IOException {
        configureSlf4jApi();
        Timer timer;
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    DataExtractor dataextractor = new DataExtractor("London,uk");
                    Weather weather = dataextractor.extractData();
                    WeatherMessageProducer msg = new WeatherMessageProducer();
                    msg.msgProducer(weather);
                } catch (IOException | InterruptedException | URISyntaxException | JMSException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 0,  15 * 60 * 1000);
    }

    private static void configureSlf4jApi() {
        BasicConfigurator.configure();
    }

}



