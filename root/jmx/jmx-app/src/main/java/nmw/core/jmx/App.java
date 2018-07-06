package nmw.core.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.ObjectName;

//import nmw.core.IEcho;

/**
 * Hello world!
 *
 */
public class App
{
	
	private static MBeanServer mBean;
    
	/**
	 * 
	 */
	public App() {
		
		super();
	}
	
	private static void init() {
		
		mBean = ManagementFactory.getPlatformMBeanServer();
		
		EchoImpl echo = new EchoImpl();
		
//			EchoMBean echo = new Echo();
		
		ObjectName mbName ;
		
		try {
			
			mbName = new ObjectName("nmw.core:type=Echo");
			mBean.registerMBean(echo, mbName);
			
			Notification nt = new Notification("Integer", echo, System.currentTimeMillis());
			
			echo.sendNotification(nt);
			
		} catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException ex ) {
			ex.printStackTrace();
		}
	}

	private static void getInput() {
		
		try {
			System.out.println("PRESS ENTER TO SHUTDOWN JMX-APP ");
			System.in.read();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main( String[] args )
    {
        System.out.println( App.class.getName() + ": Hello World!" );
        init();
        getInput();
    }

}
