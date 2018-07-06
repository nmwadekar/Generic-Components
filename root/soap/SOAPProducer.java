package nmw.core.soap;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import model.IParameter;
import model.Parameter;
import util.GenericUtil;
import util.MessageLogger;


public class SOAPProducer implements IProducer {

	//private static Logger request = MessageLogger.getRequestLogger("requestLogger");
	private static Logger responseLog = MessageLogger.getResponseLogger("responseLogger");
	private static Logger error = MessageLogger.getErrorLogger("errorLogger");
	
	@Override
	public IParameter produce(IParameter parameter, String user, String environment, String service, String message) throws Exception {

		Parameter p = new Parameter();
		System.out.println(000000000);
		SOAPConnectionFactory soapConnectionFactory = null;
		SOAPConnection soapConnection = null;
		SOAPMessage soapResponse = null;
		try {

			//System.out.println(1111);
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			//System.out.println(2222);
			soapConnection = soapConnectionFactory.createConnection();
			//System.out.println(3333);
			String url = GenericUtil.constructURL(parameter);
			//url = "http://localhost:8090/FundTechWeb/services/SanctionService";
			//System.out.println(url);
			url = url + "/services/";
			String firstChar = service.substring(0,1);			
			service = firstChar+ service.substring(1).toLowerCase();
			soapResponse = soapConnection.call(createRequest(message,user,environment), url+service+"Service");
			//soapResponse = soapConnection.call(createRequest(), url+"/service/");
			//System.out.println(5555);
			
			if(!"200".equals(soapResponse.getSOAPPart().getEnvelope().getBody().getFault().getFaultCode())){
				responseLog.info("There is some issue in requesting server." + soapResponse.getContentDescription());
				error.info("There is some issue in requesting server." + soapResponse.getContentDescription());
				p.setResponse(soapResponse.getContentDescription());
				throw new Exception("Response from the service was not appropriate. So there may some internal error at the service or the requested resource is not available.");								
			}
			System.out.println("SOAP RESPONSE: " + soapResponse.getSOAPPart().getEnvelope().getTextContent());

		} catch (Exception e) {

			error.error(e);
			throw e ;
						

		} finally {

			try {
				soapConnection.close();
			} catch (SOAPException e) {
				error.error(e);			
			}
		}
		return p;
	}

	private SOAPMessage createRequest(String requestMessage ,String user , String environment) throws SOAPException, IOException, ParserConfigurationException, SAXException {

		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();

		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPHeader header = envelope.getHeader();
		SOAPBody body = envelope.getBody();

		SOAPBody msgBody = message.getSOAPBody();
		QName bodyName = new QName("http://wombat.ztrade.com", "service", "m");
		SOAPBodyElement bodyElement = msgBody.addBodyElement(bodyName);
		
		
		if(requestMessage.startsWith("<")){//check for MX message else the message would go as it is
			requestMessage= "<![CDATA[" + requestMessage+ "]]>";
		}		

		QName name = new QName("symbol");
		SOAPElement symbol = bodyElement.addChildElement(name);
		symbol.addTextNode(requestMessage);			

		name = new QName("user");
		symbol = bodyElement.addChildElement(name);
		symbol.addTextNode(user);
		
		name = new QName("environment");
		symbol = bodyElement.addChildElement(name);
		symbol.addTextNode(environment);
		
		
		message.saveChanges();

		message.writeTo(System.out);

		System.out.println("soap request message is : "+message);

		return message;

	}
}
