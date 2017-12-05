package nmw.core.xml;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class parses the given XML file using DOM.
 * All the elements are fetched by default & then can search the values from it using provided methods.
 *
 */
public class XMLDOMParser {

	private NodeList nodes;
	
	/**
	 * @param xmlFilePath The location of the XML file to parse.
	 */
	public void initialize(String xmlSource){
		
		try{

			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(false);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			
			InputStream is = new ByteArrayInputStream( xmlSource.getBytes() );
			
			Document doc = builder.parse(is);

			nodes = doc.getElementsByTagName("*"); //-- Fetching all the elements

		} catch (ParserConfigurationException e) {
//			logger.error(e.getMessage(), e);
		} catch (SAXException e) {
//			logger.error(e.getMessage(), e);
		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
		}
	}

	//-- Not Used
	public Node getNode(String tagName) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node;
			}
		}
		return null;
	}

	//-- Not Used
	public String getNodeValue(Node node) {
		NodeList childNodes = node.getChildNodes();
		for (int x = 0; x < childNodes.getLength(); x++) {
			Node data = childNodes.item(x);
			if (data.getNodeType() == Node.TEXT_NODE)
				return data.getNodeValue();
		}
		return "";
	}

	/**
	 * @param tagName The name for which value has to be fetched directly, not searching in sub-elements.
	 * @return
	 */
	public String getNodeValue(String tagName) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				NodeList childNodes = node.getChildNodes();
				for (int y = 0; y < childNodes.getLength(); y++) {
					Node data = childNodes.item(y);
					if (data.getNodeType() == Node.TEXT_NODE)
						return data.getNodeValue();
				}
			}
		}
		return "";
	}

	//-- Not Used
	public String getNodeAttr(String attrName, Node node) {
		NamedNodeMap attrs = node.getAttributes();
		for (int y = 0; y < attrs.getLength(); y++) {
			Node attr = attrs.item(y);
			if (attr.getNodeName().equalsIgnoreCase(attrName)) {
				return attr.getNodeValue();
			}
		}
		return "";
	}

	//-- Not Used
	public String getNodeAttr(String tagName, String attrName) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				NodeList childNodes = node.getChildNodes();
				for (int y = 0; y < childNodes.getLength(); y++) {
					Node data = childNodes.item(y);
					if (data.getNodeType() == Node.ATTRIBUTE_NODE) {
						if (data.getNodeName().equalsIgnoreCase(attrName))
							return data.getNodeValue();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * @param tagName The input string to search in the sub-elements of the provided node.
	 * @param inputNode The parent node, which contains the element.
	 * @return
	 */
	public String getNodeValue(String tagName, Node inputNode) {
		
		NodeList nodes = inputNode.getChildNodes();
		
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				NodeList childNodes = node.getChildNodes();
				for (int y = 0; y < childNodes.getLength(); y++) {
					Node data = childNodes.item(y);
					if (data.getNodeType() == Node.TEXT_NODE)
						return data.getNodeValue();
				}
			}
		}
		return "";
	}
	
	/**
	 * @return the nodes
	 */
	public NodeList getNodes() {
		return nodes;
	}
}
