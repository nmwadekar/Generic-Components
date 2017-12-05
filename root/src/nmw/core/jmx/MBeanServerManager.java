package nmw.core.jmx;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

//import org.apache.catalina.mbeans.MBeanUtils;
//
//import com.bean.AppManagerBeanImpl;
//import com.jmx.IAppManagerMXBean;

//disabling it, will be enabled once required
public class MBeanServerManager {

	public static void startUp() {
		
		/*try {

			MBeanUtils.createServer().registerMBean(new AppManagerBeanImpl(), makeSingletonName(IAppManagerMXBean.class));
			
		} catch (InstanceAlreadyExistsException | NotCompliantMBeanException
				| MBeanException e) {
			e.printStackTrace();
		}*/
	}
	
	public final static ObjectName makeSingletonName(Class<?> clazz) {
        /*try {
            final Package p = clazz.getPackage();
            final String packageName = (p==null)?null:p.getName();
            final String className   = clazz.getSimpleName();
            final String domain;
            
            if (packageName == null || packageName.length()==0) {
                domain = IAppManagerMXBean.class.getSimpleName();
            } else {
                domain = packageName;
            }
            
            final ObjectName name = new ObjectName(domain,"type",className);
            
            System.out.println("Object Name created - "+name);
            
            return name;
        } catch (Exception x) {
            final IllegalArgumentException iae =
                    new IllegalArgumentException(String.valueOf(clazz),x);
            throw iae;
        }*/
		
		return null;
    }
}
