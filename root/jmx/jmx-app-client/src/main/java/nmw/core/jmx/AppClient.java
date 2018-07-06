/**
 * 
 */
package nmw.core.jmx;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author nayan
 *
 */
public class AppClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			JMXServiceURL url = 
				    new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1617/jmxrmi");
			
			JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
			
			MBeanServerConnection mbsc = 
				    jmxc.getMBeanServerConnection();
			
			ObjectName mbeanName = new ObjectName("nmw.core:type=Echo");
			EchoImplMBean mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName, 
			                                          EchoImplMBean.class, true);
			
			System.out.println(mbeanProxy.echo("JMX Client"));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
