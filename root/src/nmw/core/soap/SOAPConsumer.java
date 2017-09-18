package nmw.core.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "SOAPService")
public class SOAPConsumer{

	@WebMethod
	public String sanction(String input){		
		return null;
	}
	
	@WebMethod
	public String rejection(String input){		
		return null;
	}
}
