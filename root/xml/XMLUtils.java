package nmw.core.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xmlunit.XMLUnitException;
import org.xmlunit.diff.DifferenceEvaluator;

/**
 * @author 58000
 *
 */
public final class XMLUtils {

    public static boolean isXMLEqual(String actualXML, String expectedXML) throws Exception{

        boolean output = false;
        
        if(actualXML != null && expectedXML != null) {
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setCoalescing(true);
            dbf.setIgnoringElementContentWhitespace(true);
            dbf.setIgnoringComments(true);
            
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document actualDoc = db.parse(new InputSource(new ByteArrayInputStream(actualXML.getBytes("utf-8"))));
            actualDoc.normalizeDocument();

            Document expectedDoc = db.parse(new InputSource(new ByteArrayInputStream(expectedXML.getBytes("utf-8"))));
            expectedDoc.normalizeDocument();
            
            NodeList n1 = actualDoc.getChildNodes();
            NodeList n2 = expectedDoc.getChildNodes();
            
            Node[] a1 = convertToArray(n1);
            Node[] a2 = convertToArray(n2);
            
            NodeComparator comparator = new XMLUtils().new NodeComparator();
            
            Arrays.sort(a1, comparator);
            Arrays.sort(a2, comparator);
            
            printArrayContent(a1);
            printArrayContent(a2);
            
            System.out.println("This must be equal damn it - "+Arrays.deepEquals(a1, a2));
            
            output =  actualDoc.isEqualNode(expectedDoc);
            
            System.out.println("Iteration result = "+iterate(a1, a2));
            
        } else if(actualXML == null && expectedXML == null) {
            
            output = true;
        }
        
        return output;
    }
    
    public static void main(String[] args) throws Exception {
        
        String xml_1 = new String(Files.readAllBytes(new File("1.xml").toPath()));
        String xml_2 = new String(Files.readAllBytes(new File("2.xml").toPath()));
        
        System.out.println(isXMLEqual(xml_1, xml_2));
    }
    
    private class NodeComparator implements Comparator<Node> {

        /* (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(Node o1, Node o2) {
            
            return o1.getNodeName().equals(o2.getNodeName()) == true ? 0 : -1;
        }
        
    }
    
    private static Node[] convertToArray(NodeList e) {
        
        int nodeSize = e.getLength();
        
        Node[] output = new Node[nodeSize];
        Node tempo = null;
        
        for(int i=0; i<nodeSize; i++) {
            
            tempo = e.item(i);
            
            output[i] = tempo;
        }
        
        return output;
    }
    
    private static boolean iterate(Node[] n1, Node[] n2) {
        
            if(n1.length != n2.length)
                return false;
            
            for(int i=0; i<n1.length; i++) {
                
                if(!n1[i].equals(n2[i]))
                    return false;
            }
            
            return true;
    }
    
    private static <T> void printArrayContent(Node[] t) {
        
        for(Node o : t) {
            
            System.out.println(o.getNodeName());
        }
    }
}
