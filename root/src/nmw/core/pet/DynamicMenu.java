package nmw.core.pet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public final class DynamicMenu {

	private static String[] individualItems;

	private static String menuTreeStr;
	
	private DynamicMenu(){}
	
	public static String getMenuTree() {
		
		if(menuTreeStr == null)
			generateTree();
		
		return menuTreeStr;
	}

	private static String generateTree(){
		
		try {

			DOMParser parser = new DOMParser();
			// System.out.println(SystemProperty.getProperty("MENU_XML_PATH"));
			parser.parse(System.getProperty("MENU_XML_PATH"));
			Document doc = parser.getDocument();
			NodeList root = doc.getChildNodes();

			String menuItems = (String) System.getProperty("MENU_ITEMS");
			individualItems = menuItems.split(",");

			menuTreeStr = process(root);

		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String process(NodeList nodeList) throws FileNotFoundException, IOException {

		Node main = getNode("menu", nodeList);
		MenuTree y = new MenuTree("Menu");
		process(main, 0, y);

		StringBuffer buffer = new StringBuffer();

		processGraph(y, 1, buffer);
		
		return buffer.toString();
	}

	protected static Node getNode(String tagName, NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node;
			}
		}
		return null;
	}

	protected static String getNodeTextValue(String tagName, NodeList nodes) {
		String t = new String();
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node.getTextContent();
			}
		}
		return t;
	}

	protected static List<Node> getNodes(Node node, String nodeName) {
		NodeList childNodes = node.getChildNodes();
		List<Node> nodes = new ArrayList<Node>();
		for (int x = 0; x < childNodes.getLength(); x++) {
			Node data = childNodes.item(x);
			if (data.getNodeName().equals(nodeName)) {
				nodes.add(data);
			}
		}
		return nodes;
	}

	private static void process(Node s, int i, MenuTree menuTree) {
		if (i == individualItems.length)
			i = 0;
		List<Node> tertiary = getNodes(s, individualItems[i]);
		String value = null;
		MenuTree primary = null;
		for (Node t : tertiary) {
			value = getNodeTextValue("name", t.getChildNodes());
			primary = new MenuTree(value);
			menuTree.addLeaf(primary);
			process(t, i + 1, primary);
		}
	}

	private static MenuTree processGraph(final MenuTree tree, int p, StringBuffer buffer) {

		int chilElementSize = tree.getElements().size();

		MenuTree currentElement = null;

		if (chilElementSize > 0)
			buffer.append("<UL id=\"" + tree.getNodeName()/* .replace(" ", "_") */ + p + "\">");

		for (int i = 0; i < tree.getElements().size(); i++) {

			currentElement = tree.getElements().get(i);

			buffer.append("<LI id=\""
					+ currentElement.getNodeName()/* .replace(" ", "_") */ + (p + 1) + "\"><A name=\""
					+ currentElement.getNodeName() + "\" title=\""
					+ currentElement.getNodeName()/* .replace(" ", "_") */ + "\" href=\"environment.do?actionParameter="+currentElement.getNodeName()+"\">"
					+ currentElement.getNodeName()/* .replace(" ", "_") */ + "</A>");

			processGraph(currentElement, p + 1, buffer);

			buffer.append("</LI>");
		}

		if (chilElementSize > 0)
			buffer.append("</UL>");

		return currentElement;
	}
}
