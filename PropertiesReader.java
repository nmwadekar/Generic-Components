package util;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {

	public static Map<String, String> getPropertyMap(String filePath) {
		Map<String, String> environments = new HashMap<String, String>();
		try {
			FileInputStream fis = new FileInputStream(filePath);
			Properties properties = new Properties();
			properties.load(fis);
			fis.close();

			Enumeration<Object> env = properties.keys();
			while (env.hasMoreElements()) {
				String key = (String) env.nextElement();
				String value = properties.getProperty(key);
				environments.put(key, value);
				// System.out.println(key + ": " + value);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception caught in PropertiesReader.getEnvironments()");
			e.printStackTrace();
			String exception = e.getMessage();
			System.out.println(exception);
		}

		return environments;
	}
}
