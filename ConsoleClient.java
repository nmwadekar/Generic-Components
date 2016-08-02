package nmw.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author 58000
 *
 */
public class ConsoleClient extends UnicastRemoteObject implements ClientInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 384804008037733214L;
	/**
	 * 
	 */
	private String name;
	
	/**
	 * @param name
	 * @throws RemoteException 
	 */
	public ConsoleClient(String name) throws RemoteException {
		super();
		this.name = name;
	}

	public void print(String msg) {
		System.out.println(msg);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
}
