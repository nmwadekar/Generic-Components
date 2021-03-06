package nmw.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author Nayan Wadekar
 *
 */
public class QueueBrowser {

	public static void main(String[] args) throws JMSException {
		
		ConnectionFactory factory = new ActiveMQConnectionFactory(
                "tcp://" + "localhost" + ":" + "61616");
		
		Connection conn = factory.createConnection("admin", "admin");
		conn.start();
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("tempo");
        
        javax.jms.QueueBrowser browser = session.createBrowser(queue);
        
        Enumeration msgs = browser.getEnumeration();
        
        if ( !msgs.hasMoreElements() ) { 
            System.out.println("No messages in queue");
        } else { 
            while (msgs.hasMoreElements()) { 
                Message tempMsg = (Message)msgs.nextElement(); 
                System.out.println("Message: " + tempMsg); 
            }
        }
	}
}
