package presenter;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * PropertiesHandler save and load the properties
 * 
 * @author bar brownshtein
 *
 */
public class PropertiesHandler {
	private static Properties properties;

	/**
	 * if there is exist properties we used him, else we write another one with
	 * default properties
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static Properties getInstance() throws FileNotFoundException, Exception {
		if (properties == null) {

			properties = read("properties.xml");
		
		}

		return properties;
	}

	/**
	 * write
	 * 
	 * @param p
	 * @param filename
	 * @throws Exception
	 */
	public static void write(Properties p, String filename) throws Exception {
	
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		encoder.writeObject(p);
		encoder.flush();
	
		encoder.close();
	}

	/**
	 * read
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static Properties read(String filename) throws Exception {

		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
		Properties o = (Properties) decoder.readObject();
		
		
		
		decoder.close();
		return o;
	}
}
