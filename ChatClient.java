package nmw.rmi;

import java.rmi.Naming;
//import java.rmi.RMISecurityManager;
//import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

/**
 * @author 58000
 *
 */
public class ChatClient {

	private ClientInterface client;
	
	/**
	 * 
	 */

	public static void main(String[] argv) {
		
		ChatClient chatClient = new ChatClient();
		
		chatClient.start();
	}
	
	public void start() {
		
		try {
			
//			System.setProperty("java.security.policy","file:///C:/eclipse_worspace_20072016/RMIExample/src/nmw/rmi/security.policy");
//			System.setSecurityManager(new RMISecurityManager());
			
			Scanner s = new Scanner(System.in);
			System.out.println("Enter Your name and press Enter:");
			String name = s.nextLine().trim();
			this.client = new ConsoleClient(name);

			ChatServerInterface server = (ChatServerInterface) Naming.lookup("rmi://localhost/ABC");
			
			System.out.println("[System] Chat Remote Object is ready:");
			server.setClient(client);
			
			String msg = "[" + client.getName() + "] got connected";
			server.send(msg);
			
			while (true) {
				msg = s.nextLine().trim();
				msg = "[" + client.getName() + "] " + msg;
				server.send(msg);
			}

		} catch (Exception e) {
			System.out.println("[System] Server failed: " + e);
			e.printStackTrace();
		}
	}
	
	public void print(String msg) {
		
		System.out.println(msg);
	}
}
