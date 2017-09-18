package nmw.core.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nayan.M.Wadekar
 *
 */
public class ChatImpl  extends UnicastRemoteObject implements ChatServerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4473776260906078824L;

	protected ChatImpl() throws RemoteException {
		super();
	}

	/**
	 * 
	 */
	
	private List<ClientInterface> client = new ArrayList<ClientInterface>();

	/* (non-Javadoc)
	 * @see nmw.rmi.ChatInterface#send(java.lang.String)
	 */
	@Override
	public void send(String msg) throws RemoteException {
		
		for(ClientInterface c : client)
			c.print(msg);
	}
	
	/* (non-Javadoc)
	 * @see nmw.rmi.ChatInterface#setClient(nmw.rmi.ChatInterface)
	 */
	@Override
	public void setClient(ClientInterface c) throws RemoteException {

		client.add(c);
	}

}
