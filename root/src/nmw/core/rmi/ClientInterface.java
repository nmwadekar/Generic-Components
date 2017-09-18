package nmw.core.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author 58000
 *
 */
public interface ClientInterface extends Remote {

	public void print(String msg) throws RemoteException;
	public String getName() throws RemoteException;
}
