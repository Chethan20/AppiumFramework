package utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Chethan
 *
 */
public final class PropertyReader {

	private static Properties properties = new Properties();
	private static final String configfile = "config";

	/**
	 * Reads config file
	 * @return
	 */
	public static Properties loadPropertyfile() {
		setPropertyFile(configfile);
		return properties;
	}

	private static Properties setPropertyFile(String filename) {
		String filepath = System.getProperty("user.dir") + filename + ".properties";

		try {
			FileReader readfile = new FileReader(filepath);
			try {
				properties.load(readfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
