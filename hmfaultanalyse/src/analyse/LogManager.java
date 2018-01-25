/*
********************************************************************************************
Discription:  org.apache.log4j日志应用
			  
Written By:   ZXZ
Date:         2014-05-30
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/

package analyse;

import org.apache.log4j.PropertyConfigurator;

public class LogManager {
	public static final String LOGGER_NAME_EXCEPTION = "nameException";
	public static final String LOGGER_NAME_OTHERS = "nameOthers";
	public static final String LOGGER_NAME_DEBUG = "nameDebug";
	
	public static void init(){
		String filePath = "config/log4j.properties";
		PropertyConfigurator.configure(filePath);
	}
}