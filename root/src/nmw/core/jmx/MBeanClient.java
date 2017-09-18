package nmw.core.jmx;

import java.io.IOException;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class MBeanClient {

	public static Class<?> classType;
	
	public <T> MBeanClient(Class<?> t) {
		super();
		classType = t;
	}

	@SuppressWarnings("unchecked")
	public <T> T fetchProxy(){
		
		try {
			
			ObjectName beanName = new ObjectName("com.jmx:type=IAppManagerMXBean");			
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1617/jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url);			
			MBeanServerConnection jmxConn = jmxc.getMBeanServerConnection();						
			T proxy =
	                 (T) JMX.newMXBeanProxy(jmxConn,beanName,
					classType, true);						
			System.out.println("IS this registered - "+jmxConn.isRegistered(beanName));
			
//			System.out.println(MBeanUtils.createServer().isRegistered(beanName));
			
			return proxy;
			
		} catch (IOException ex) {
			ex.printStackTrace();
			
		}
		catch (MalformedObjectNameException ex) {
			ex.printStackTrace();
			// MalformedObjectNameException ex
		}
		return null;
	}
}