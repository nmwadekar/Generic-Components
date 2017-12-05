package nmw.core.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONUtils {

    private final static String NODE_RELATIVE_PATH = "NODE_RELATIVE_PATH";
    private final static String NODE_FIELD_NAME = "NODE_FIELD_NAME";
    private final static String NODE_SEPARATOR = "/";
    
    /*public static <T> T fetchNodeFromJSON(String input, String path) throws JsonParseException, JsonMappingException, IOException{
        
        JsonNode root = new ObjectMapper().readTree(input);
        
        JsonNode node = root.at(path);
        
        return extractResultNodeType(node);
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T extractResultNodeType(JsonNode node) throws IOException{
        
        if(node == null || node.isNull() || node.isMissingNode())
            return null;
        
        ObjectMapper objMapper = new ObjectMapper();        
        String resultingJSON = node.toString();
        
        if( (node instanceof ArrayNode))
        {
            return (T) objMapper.readValue(resultingJSON, ArrayList.class);
            
        }else if(node instanceof ObjectNode){
            
            return (T) objMapper.readValue(resultingJSON, HashMap.class);
        }
        
        if(node.isBigDecimal()) {
            
            return (T)node.decimalValue();
            
        }else if(node.isTextual()) {
            
            return (T)node.textValue();
            
        }else if(node.isNumber()) {
            
            return (T) new BigDecimal(resultingJSON);
        }
        else 
            throw new RuntimeException("Node Datatype not yet implemented: "+node);
        
    }
    
    public static <T> T fetchNodeFromSubJSON(final String input, final String path, String innerJSONKey) throws JsonParseException, JsonMappingException, IOException, URISyntaxException{
        
        if(path.contains(innerJSONKey)){
            
            int indexOfKey = path.indexOf(innerJSONKey)+innerJSONKey.length();
            
            String keyString = path.substring(0, indexOfKey);
            
            String innerJSONBlock = fetchNodeFromJSON(input, keyString);

            String remainingPart = path.substring(indexOfKey, path.length());
            
            return fetchNodeFromJSON(innerJSONBlock, remainingPart);
            
        }else{
            
            return fetchNodeFromJSON(input, path);
        }
    }
    
    public static void updateNodes(JsonNode rootNode, Map<String, Object> fieldValues){
        
        if(fieldValues == null || fieldValues.isEmpty())
            return;
        
        JsonNode temporaryHolder = null;
        Object modifiedValue = null;
        Map<String, String> nodeDetails = null;
        
        String NULL_VALUE = JSONTemplateProcessor.getNullIndicator();
        
        String relativePath = null;
        boolean isNullValue = false;
        String nodeName = null;
        ObjectNode on = null;
        
        for(Entry<String, Object> entry : fieldValues.entrySet()){

            isNullValue = false;
            
            if(NULL_VALUE.equals(entry.getValue())){
                
                isNullValue = true;
            }else{
                modifiedValue = entry.getValue();
            }

            nodeDetails =  getFieldNamePath(entry.getKey());
            relativePath = nodeDetails.get(NODE_RELATIVE_PATH);
            temporaryHolder = rootNode.at(relativePath);
            nodeName = nodeDetails.get(NODE_FIELD_NAME);
            
            if(temporaryHolder.isMissingNode())
                throw new IllegalArgumentException("NODE PATH NOT FOUND: " + relativePath);
            
            if(temporaryHolder instanceof ObjectNode && !isNullValue){
                
                on = (ObjectNode)temporaryHolder;
                
                if(modifiedValue instanceof BigDecimal) {
                    
                    on.put(nodeName, (BigDecimal)modifiedValue);
                
                }else if(modifiedValue instanceof String) {
                        
                    on.put(nodeName, (String)modifiedValue);
                    
                }else if(modifiedValue instanceof JsonNode) {
                    
                    on.set(nodeName, (JsonNode)modifiedValue);
                    
                }else { //-- Converting generic object to JsonNode type & replacing it with existing node
                    
                    JsonNode node = new ObjectMapper().convertValue(modifiedValue, JsonNode.class);
                    on.set(nodeName, node);
                }
                else 
                    throw new RuntimeException("Object type not implemented - " + modifiedValue);
                
            }else if(temporaryHolder instanceof ObjectNode && isNullValue){
                
                ((ObjectNode)temporaryHolder).putNull(nodeName);
            }
            else {
                throw new RuntimeException("Invalid node, can only set for ObjectNode: " + relativePath);
            }
        }
    }
    
    public static void removeNodes(JsonNode rootNode, List<String> nodesPath){
        
        if(nodesPath == null || nodesPath.isEmpty())
            return;
        
        JsonNode temporaryHolder = null;
        Map<String, String> nodeDetails = null;

        for(String path : nodesPath){
            
            nodeDetails = getFieldNamePath(path);
            
            temporaryHolder = rootNode.at(nodeDetails.get(NODE_RELATIVE_PATH));
            
            if(!temporaryHolder.isMissingNode())
                ((ObjectNode)temporaryHolder).remove(nodeDetails.get(NODE_FIELD_NAME));
            else
                throw new IllegalArgumentException("NODE PATH NOT FOUND: " + nodeDetails.get(NODE_RELATIVE_PATH));
        }
    }
    
    private static Map<String, String> getFieldNamePath(String path){
        
        int i = path.lastIndexOf(NODE_SEPARATOR);
        
        String relativePath = path.substring(0, i);
        String fieldName = path.substring(i+1, path.length());
        
        Map<String, String> result = new HashMap<String, String>();
        
        result.put(NODE_RELATIVE_PATH, relativePath);
        result.put(NODE_FIELD_NAME, fieldName);
        
        return result;
    }
    
    public static boolean isErrorEventFound(List<String> eventMessages, String code, String message, String requestId, String errorDetailsPath) throws Throwable {

        String nodeValue = null;
        String o_paymentId = null;
        String NULL_INDICATOR = JSONTemplateProcessor.getNullIndicator();
        
        List<Entry<?, ?>> entries = new ArrayList<Entry<?, ?>>();
        
        try {

            if(isNull(eventMessages) || eventMessages.isEmpty())
                return false;

                Object errorDetails = null;
                SimpleEntry<String, String> entry;

                List<String> errorEvents = new ArrayList<String>();
                
                for (String e : eventMessages) {
                    
                    nodeValue = fetchNodeFromJSON(e, "/severity");
                    o_paymentId = fetchNodeFromJSON(e, "/externalRequestId");

                    if(NULL_INDICATOR.equals(requestId))
                        requestId = null;
                    
                    if ("ERROR".equals(nodeValue) &&
                            ((requestId != null && requestId.equals(o_paymentId)) || (requestId == null))) {
                        
                        errorEvents.add(e);
                    }
                }
                
                if(errorEvents.size()  != 1)
                    return false;
                
                String e = errorEvents.get(0);
                
                        errorDetails = fetchNodeFromJSON(e, errorDetailsPath);

                        if(code != null){
                            entry = new SimpleEntry<String, String>("errorCode", code);
                            entries.add(entry);
                        }
                        
                        if(message != null){
                        
                            entry = new SimpleEntry<String, String>("errorMessage", message);
                            entries.add(entry);
                        }
                    
                        return CommonUtils.isElementFound(errorDetails, entries);
                
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static boolean isLeafNodePresent(String input, String path) throws JsonProcessingException, IOException{

    	JsonNode root = new ObjectMapper().readTree(input);

        JsonNode temporaryHolder = null;
    	Map<String, String> nodeDetails = null;
    	
    	nodeDetails = getFieldNamePath(path);
    	
    	temporaryHolder = root.at(nodeDetails.get(NODE_RELATIVE_PATH));
        if(!temporaryHolder.isMissingNode()){
        	if(temporaryHolder.has(nodeDetails.get(NODE_FIELD_NAME)))
				return true;
        }
            
        else
            throw new IllegalArgumentException("NODE PATH NOT FOUND: " + nodeDetails.get(NODE_RELATIVE_PATH));
    	

    	return false;
    }
    
    public static <M, N> boolean isJSONEqual(M expectedJSON, N actualJSON) throws JsonProcessingException, IOException {
        
        boolean output = false;
        
        if(expectedJSON != null && actualJSON!= null) {

            JsonNode expNode = convertToJSONNode(expectedJSON);
            JsonNode actualNode = convertToJSONNode(actualJSON);
                       
            output = expNode.equals(actualNode);
            
        }else if(expectedJSON == null && actualJSON == null){
            
            output = true;
        }
        
        return output;
    }
    
    private static JsonNode convertToJSONNode(Object jsonObject) throws JsonProcessingException, IOException {
        
        ObjectMapper mapper = new ObjectMapper();
        
        if(jsonObject instanceof String)
            jsonObject = convertJsonStringToNode((String)jsonObject);
        
        else if(jsonObject instanceof JsonNode)
            jsonObject = (JsonNode)jsonObject;
        
        else if(jsonObject instanceof List || jsonObject instanceof Map) {
            
            String oString = mapper.writeValueAsString(jsonObject);
            jsonObject = convertToJSONNode(oString);
        }
        
        return (JsonNode)jsonObject;
    }
    
    public static JsonNode convertJsonStringToNode(String input) throws JsonProcessingException, IOException {
        
        if(input == null)
            return null;
        
        ObjectMapper mapper = new ObjectMapper();
        
        return mapper.readTree(input);
    }*/
}
