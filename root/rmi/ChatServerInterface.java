package nmw.core.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author 58000
 *
 */
public interface ChatServerInterface extends Remote {

	public void send(String msg) throws RemoteException;
	public void setClient(ClientInterface c)throws RemoteException;
}
