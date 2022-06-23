import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

import javax.jms.*;

public class Main {

    public static void main(String[] args) throws JMSException {
        configureSlf4jApi();
        Connection connection = startConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("sensor.Weather");
        String directoryBD = args[0] + "\\dataBase";
        receiveMessage(session, topic, directoryBD);
    }

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void receiveMessage(Session session, Topic topic, String directoryBD) throws JMSException {
        MessageConsumer consumer = session.createDurableSubscriber(topic,"analytics" );
        MessageListener listener = new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String event = textMessage.getText();
                    Weather weather = parseMessage(event);
                    WeatherDB manager =  new WeatherDB(directoryBD);
                    manager.storageData(weather);
                    LineChart lineChart = new LineChart(directoryBD);
                    lineChart.exploitData();
                    PythonModelCaller pythonModelCaller = new PythonModelCaller();
                    pythonModelCaller.callPython(directoryBD);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        consumer.setMessageListener(listener);
    }

    private static Weather parseMessage(String event) {
        JsonObject gson = new Gson().fromJson(event, JsonObject.class);
        Deserializer deserialize = new Deserializer(gson);
        return deserialize.buildWeatherFromJson();
    }

    public static Connection startConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("analyticsClient");
        connection.start();
        return connection;
    }

    private static void configureSlf4jApi() {
        BasicConfigurator.configure();
    }


}
