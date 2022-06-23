import javax.jms.JMSException;

public interface EventSender {
    void msgProducer(Weather weather) throws JMSException;
}
