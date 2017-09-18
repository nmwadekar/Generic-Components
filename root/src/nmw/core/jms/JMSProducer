package nmw.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public abstract class JMSProducer<E extends JMSAPIParam> {

    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private Long waitDuration;
    
    public void  initialize(E input) throws NumberFormatException, JMSException{  
        
        ConnectionFactory cf = createConnectionFactory(input);
        
        createProducer(input, cf);
        
        waitDuration = input.getProducerWaitDuration();
        
        if(waitDuration == null) 
            waitDuration = 0l;
    }

    protected abstract ConnectionFactory createConnectionFactory(E parameter) throws NumberFormatException, JMSException;

    protected void createProducer(E parameter, ConnectionFactory factory) throws JMSException{
            
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Destination requestDestination = session.createQueue(parameter.getRequestQueue());
        producer = session.createProducer(requestDestination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }
    
    public void sendMessage(String message) {
        
        try {
            
            TextMessage jmsMessage = session.createTextMessage(message);
            producer.send(jmsMessage);
            
            CommonUtils.addDelay(waitDuration);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
