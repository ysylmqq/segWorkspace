package cc.chinagps.seat.util;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class PropertiesReader {
	
	private static final Logger LOGGER = Logger.getLogger(
			PropertiesReader.class);
	
	/**
	 * 读取properties文件
	 * @param propertiesFile
	 * @return
	 */
	public static Properties readProperties(String propertiesFile) {
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(new File(propertiesFile)));
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return prop;
	}
}
