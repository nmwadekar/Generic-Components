package nmw.core.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author 58000
 *
 */
public class ChatServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
//			System.setProperty("java.security.policy","file:///C:/eclipse_worspace_20072016/RMIExample/src/nmw/rmi/security.policy");
			
//			System.setSecurityManager(new RMISecurityManager());
			
			LocateRegistry.createRegistry(1099);
			
			ChatServerInterface server = new ChatImpl();

			Naming.rebind("rmi://localhost/ABC", server);

			System.out.println("[System] Chat Remote Object is ready:");
			
		} catch (Exception e) {
			System.out.println("[System] Server failed: " + e);
			e.printStackTrace();
		}
	}

}
