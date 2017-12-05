package nmw.core.jms;

import java.io.UnsupportedEncodingException;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import nmw.core.jms.JMSAPIParam;

//<> needs update
public abstract class JMSConsumer {
    
    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private Long waitDuration;
    
    public void initialize(JMSAPIParam parameter) throws NumberFormatException, JMSException{  
        
        ConnectionFactory cf = createConnectionFactory(parameter);
        
        createConsumer(parameter, cf);
    }

    protected abstract ConnectionFactory createConnectionFactory(JMSAPIParam parameter) throws NumberFormatException, JMSException;

    protected void createConsumer(JMSAPIParam parameter, ConnectionFactory factory) throws JMSException{
            
        waitDuration = parameter.getConsumerWaitDuration();
        
        if(waitDuration == null || waitDuration.longValue() == 0l){
            waitDuration = null;
        }
        
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        Destination requestDestination = session.createQueue(parameter.getReplyQueue());
        consumer = session.createConsumer(requestDestination);
    }
    
    public String consumeMessage() throws JMSException, UnsupportedEncodingException {

        BytesMessage bytesMessage = null;
        if(waitDuration == null){
        	bytesMessage = (BytesMessage)consumer.receiveNoWait();
        }else{
        	bytesMessage=  (BytesMessage)consumer.receive(waitDuration);
        }

        if(bytesMessage != null){
            
            byte[] byteArray = new byte[(int) bytesMessage.getBodyLength()];

            bytesMessage.readBytes(byteArray);

            String msg = new String(byteArray, "UTF-8");

            return msg;
        } 
        
        return null;
    }
    
    public void clearQueue() throws JMSException {

        while (consumer.receiveNoWait() != null);
    }
}
