package test.android.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class PropertyReader {

	private static Properties properties = new Properties();
	private static final String configfile = "config";
	private static final String testdatafile = "testdata";

	public static Properties loadPropertyfile() {
		setPropertyFile(configfile);
		return properties;
	}
	
	public static Properties loadTestData() {
		setPropertyFile(testdatafile);
		return properties;
	}

	private static Properties setPropertyFile(String filename) {
		String filepath = System.getProperty("user.dir") + "\\src\\main\\java\\test\\android\\resources\\" + filename + ".properties";

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
