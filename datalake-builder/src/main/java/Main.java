import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

import javax.jms.*;
import java.io.IOException;

public class Main {

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException, IOException {
        configureSlf4jApi();
        Connection connection = startConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("sensor.Weather");
        String directorySensor = args[0];

        receiveMessage(topic, session, directorySensor);
    }

    private static void configureSlf4jApi() {
        BasicConfigurator.configure();
    }

    private static void receiveMessage(Topic topic, Session session, String directorySensor) throws JMSException {
        MessageConsumer consumer = session.createDurableSubscriber(topic,"datalake-builder" );
        MessageListener listener = new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String event = textMessage.getText();
                    JsonFileWritter fileWritter = new JsonFileWritter(event, directorySensor);
                    fileWritter.writeMessage();
                } catch (JMSException | IOException e) {
                    e.printStackTrace();
                }
            }
        };
        consumer.setMessageListener(listener);
    }

    private static Connection startConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("datalake-builderClient");
        connection.start();
        return connection;
    }
}
