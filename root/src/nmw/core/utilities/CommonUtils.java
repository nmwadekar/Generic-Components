package nmw.core.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class CommonUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");//2016-03-30T12:36:57.SSSz
    
	private static Properties createPropertyFile(String path) throws IOException {
		
	    if(isNull(path))
	        return null;
	    
		Properties properties = new Properties();
		
		FileInputStream fis = new FileInputStream(new File(path)); 
	        
	        properties.load(fis);
		    
            fis.close();
		
		return properties;
	}
	
	@SuppressWarnings("unchecked")
    public static <E> boolean isElementFound(E source, List<Entry<?, ?>> entry){
	    
	    if(source instanceof List){
	        
	        LinkedHashMap<String, String> lhm = null;
	        
	        for(E s: (List<E>)source){
	            
	            if(s instanceof LinkedHashMap<?, ?>){
	                
	                lhm = (LinkedHashMap<String, String>)s;
	                
	                if(isMapContainEntries(lhm, entry)){
	                    
	                    return true;
	                }
	                    
	            }
	        }
	        
	    }else if(source instanceof Map){
            
	        return isMapContainEntries((Map<String, String>)source, entry);
        }
	    
	    return false;
	}
	
	private static boolean isMapContainEntries(Map<?, ?> input, List<Entry<?, ?>> entry){
	    
	    boolean isFound = true;
	    
        for(Entry<?, ?> e : entry){
            
            if(!isEntryInMap(input, e)){
                isFound = false;
                break;
            }
        }
        
       return isFound;
	}
	
	public static boolean isEntryInMap(Map<?, ?> input, Entry<?, ?> entry){
	    
	    return (input.containsKey(entry.getKey()) && input.containsValue(entry.getValue()));
	}
	
	public static String getResourcePath(String resourceName) throws URISyntaxException {
	    
	    URL resourceLRL = CommonUtils.class.getClassLoader().getResource(resourceName);
        
	    if(isNotNull(resourceLRL)){
	        return resourceLRL.toURI().getPath();
	    }
	    else 
	        return null;
	}
	
	public static Properties getPropertyFile(String fileNameWithRelativePath) throws URISyntaxException, IOException{
		
	    return createPropertyFile(getResourcePath(fileNameWithRelativePath));
	}
	
	public static Map<String, String> getPropertyFileMap(String fileNameWithRelativePath) throws URISyntaxException, IOException{
        
	    Properties p = getPropertyFile(fileNameWithRelativePath);
	    
	    if(isNull(p))
	        return null;
	    
	    Map<String, String> m = new HashMap<String, String>();
	    
	    for(Entry<?,?> e : p.entrySet()){
	        
	        m.put(e.getKey().toString(), e.getValue().toString());
	    }
	    
	    return m;
	}
	
	public static String getPropertyFileObject(String fileNameWithRelativePath, String key) throws URISyntaxException, IOException{
        
        Properties p = getPropertyFile(fileNameWithRelativePath);
        
        return p.get(key).toString();
    }
	
	public static String getPropertyValue(Properties p, String key){
	    
	    return p.get(key).toString();
	}
	
	public static boolean isNull(Object o){
	    
	    return (o == null ? true:false);
	}
	
	public static boolean isNotNull(Object o){
        
        return (o != null ? true:false);
    }
	
	public static Object returnNullIfNullElseObject(Object o){
	    
	    return (isNull(o) == true ? null:o);
	}
	

	public static Map<String, String> getEnvironmentConfig(String fileName) throws URISyntaxException, IOException{
	    
	    Map<String, String> propMap = getPropertyFileMap(fileName);
	    
	    Map<String, String> envMap = System.getenv();
	    
	    if(propMap != null){
	        propMap.putAll(envMap);
            return propMap;
	    }else 
	        return envMap;
	}
	
	public static String getDateAsISOString(Date d){
	    
	    return DATE_FORMAT.format(d);
	}
	
	public static String getDateAsISOTimestampString(Date timestamp){
	        
	        return TIMESTAMP_FORMAT.format(timestamp);
	}

	public static String getCurrentDateAsISOString(){
	        
	        return getDateAsISOString(new Date());
	}
	
	public static String getCurrentTimestampAsISOString(){
           
           return getDateAsISOTimestampString(new Date());
   }

   public static String getFileContent(String resourceRelativePath) throws URISyntaxException, IOException{
       
       URL resourceLRL = CommonUtils.class.getClassLoader().getResource(resourceRelativePath);
       
       Path resourcePath = Paths.get(resourceLRL.toURI());
       
       String request = new String(Files.readAllBytes(resourcePath), "UTF-8");
       
       return request;
   }
   
   public static BigDecimal getNumericValue(String value, String nullIndicator) {
       
       BigDecimal numericValue = null;
       
       if(value != null && !nullIndicator.equals(value)) {
           
           numericValue = new BigDecimal(value);
           
           if("0.00".equals(value))
               numericValue = numericValue.setScale(2, RoundingMode.CEILING);
       }
       
       return numericValue;
   }
   
   public static Object returnNullIfNullString(String input, String nullIndicator){
       
       if(input == null || nullIndicator.equals(input))
           return null;
       else
           return input;
   }

   public static void addDelay(long sleepDuration) throws RuntimeException{
       
       try {
           Thread.sleep(sleepDuration);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
   }
}
