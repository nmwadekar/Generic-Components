package nmw.core.rest;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

import model.IParameter;
import model.Parameter;
import util.GenericUtil;
import util.MessageLogger;

public class RESTProducer implements IProducer {

	private static Logger request = MessageLogger.getRequestLogger("requestLogger");
	private static Logger responseLog = MessageLogger.getResponseLogger("responseLogger");
	private static Logger error = MessageLogger.getErrorLogger("errorLogger");
	
	@Override
	public IParameter produce(IParameter parameter, String user, String environment, String service, String message)throws Exception {
		
		Parameter p = new Parameter();	
		try{
			request.info("Executing produce1() of RestService");
			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			URI serviceURI = UriBuilder.fromUri(GenericUtil.constructURL(parameter)).build();
			WebTarget webTarget = client.target(serviceURI);
			//WebTarget webTarget2 = client.target(serviceURI);
			
			request.info("url to be sent is: "+ webTarget.getUri().toString());
			String messageExtended = user + ";;;;" + environment + ";;;;" + message;
				Response outPut_1 = webTarget.path("/restService/"+service.toLowerCase()).request(MediaType.TEXT_PLAIN).post(Entity.entity(messageExtended, MediaType.TEXT_PLAIN));
				//System.out.println("***********************Response received in RestProducer:" + outPut_1 + "*********************************");
				
				System.out.println("######################"+webTarget.getUri().toURL());
				
				String response = outPut_1.readEntity(String.class);
				responseLog.info("############# Response from Service: "+ response);
				
				if(outPut_1.getStatus() != 200){
					
					responseLog.info("There is some issue in requesting server." + outPut_1.getHeaders());
					error.info("There is some issue in requesting server." + outPut_1.getHeaders());
					throw new Exception("Response from the service was not appropriate. So there may some internal error at the service or the requested resource is not available.");
				}
			
			
			p.setResponse(response);
			
			return p;
		
			}catch(Exception e){
				
				error.error(e);
				throw e;
			}
		
	}
}