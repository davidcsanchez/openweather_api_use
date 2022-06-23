import javax.jms.*;
import javax.jms.MessageProducer;

import org.apache.activemq.ActiveMQConnectionFactory;

public class WeatherMessageProducer implements EventSender{

    public final String url = "failover://tcp://localhost:61616";

    public WeatherMessageProducer() {
    }

    public void msgProducer(Weather event) throws JMSException {
        Serialize serializer = new Serialize();
        Connection connection = startConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination sessionTopic = session.createTopic("sensor.Weather");
        sendMessage(sessionTopic, session, serializer.weatherToJsonFormat(event));
        closeConnection(connection);
    }

    private void sendMessage(Destination sessionTopic, Session session, String serializedevent) throws JMSException {
        MessageProducer producer = session.createProducer(sessionTopic);
        TextMessage message = session.createTextMessage(serializedevent);
        producer.send(message);
    }

    private void closeConnection(Connection connection) throws JMSException {
        connection.close();
    }

    private Connection startConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection;
    }
}
