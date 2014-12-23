/**
 * 
 */
package util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.FileHandler;

/**
 * @author annvcit
 * @full_name Nguyen Van Chuc An
 * @date_created Dec 23, 2014
 * @email annvcit@gmail.com
 */
public class PropertyLoader {
	
	public static String loadProperty(String propertyName) {
		String value = null;
		Properties p = new Properties();
		try {
			InputStream is = FileHandler.class.
					getClassLoader().getResourceAsStream("config.properties");
			p.load(is);
			value = p.getProperty(propertyName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
