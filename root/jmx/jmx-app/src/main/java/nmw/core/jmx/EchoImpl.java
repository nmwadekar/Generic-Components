/**
 * 
 */
package nmw.core.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

import nmw.core.Echo;

/**
 * @author nayan
 *
 */
public class EchoImpl extends NotificationBroadcasterSupport implements EchoImplMBean {

	/* (non-Javadoc)
	 * @see nmw.core.Echo#echo(java.lang.String)
	 */
	
	private Echo e = new Echo();
	
	@Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{
            AttributeChangeNotification.ATTRIBUTE_CHANGE
        };

        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = 
                new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }

	/* (non-Javadoc)
	 * @see javax.management.NotificationBroadcasterSupport#addNotificationListener(javax.management.NotificationListener, javax.management.NotificationFilter, java.lang.Object)
	 */
	@Override
	public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
		// TODO Auto-generated method stub
		super.addNotificationListener(new NotificationListener() {
			
            @Override

            public void handleNotification(Notification notification, Object handback) {

                System.out.println("*** Handling new notification ***");

                System.out.println("Message: " + notification.getMessage());

                System.out.println("Seq: " + notification.getSequenceNumber());

                System.out.println("*********************************");

            }

        }, null, null);
	}

	
	@Override
	public String echo(String input) {
		// TODO Auto-generated method stub
		
		Notification nt = new Notification("Integer", "INPUT = "+ input, System.currentTimeMillis(), input);
		sendNotification(nt);
		
		return e.echo(input);
	}

	/**
	 * 
	 */
	public EchoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
}
