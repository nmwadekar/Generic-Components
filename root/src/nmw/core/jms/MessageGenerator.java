package nmw.core.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import model.IParameter;
import model.MessageParameter;
import util.GenericUtil;

public class MessageGenerator implements IProducer {

	private final int RETRY_COUNT = 3;

	@Override
	public IParameter produce(IParameter parameter, String user, String environment, String service, String msg) {

		MessageParameter messageParameter = (MessageParameter) parameter;
		
		int currentCount = 1;
		boolean flagToRetry = true;
		
		
		Session session = null;
		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				GenericUtil.constructURL(messageParameter));
	
		Connection connection = null;
		Destination destination = null;
		MessageProducer producer = null;
		TextMessage message = null;

		while (currentCount <= RETRY_COUNT && flagToRetry) {

			try {
				connection = connectionFactory.createConnection();
				
				connection.start();
				
				session = connection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
				
				destination = session.createQueue(messageParameter.getQueueName());
				
				producer = session.createProducer(destination);
				
				message = session.createTextMessage(messageParameter.getQueueName());
				
				producer.send(message);
				
				connection.stop();
				
				flagToRetry = false;
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
				currentCount++;
				flagToRetry = true;
				
			} finally{
				
				try {
					producer.close();
					session.close();
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
}
