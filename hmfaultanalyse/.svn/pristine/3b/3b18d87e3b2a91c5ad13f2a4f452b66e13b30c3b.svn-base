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

package logs;

import org.apache.log4j.PropertyConfigurator;

public class LogManager {
	public static void init(){
		String filePath = "config/log4j.properties";
		PropertyConfigurator.configure(filePath);
    	System.out.println("log4j config initialized");
	}
	
	/*public static void main(String[] args) {
		init();
		
		Logger logger1 = Logger.getLogger("nameCmd");
		Logger logger2 = Logger.getLogger("nameUnkownCmd");
		Logger logger3 = Logger.getLogger("nameException");
		Logger logger4 = Logger.getLogger("nameAlarm");
		Logger logger5 = Logger.getLogger("nameOthers");
		Logger logger6 = Logger.getLogger("nameDebug");
		
		int count = 1000;
		long t1, t2, t;
		String head = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		t1 = System.currentTimeMillis();
		for(int i = 0; i < count; i++){
			logger1.info("type(cmd)" + head + i);
		}
		t2 = System.currentTimeMillis();
		t = t2 - t1;
		System.out.println("logger(cmd):" + t + ", speed:" + (1000.0 * count / t));
		
		
		t1 = System.currentTimeMillis();
		for(int i = 0; i < count; i++){
			logger2.info("type(unkownCmd)" + head + i);
		}
		t2 = System.currentTimeMillis();
		t = t2 - t1;
		System.out.println("logger(unkownCmd):" + t + ", speed:" + (1000.0 * count / t));
		
		
		t1 = System.currentTimeMillis();
		for(int i = 0; i < count; i++){
			logger3.warn("type(exception)" + head + i);
		}
		t2 = System.currentTimeMillis();
		t = t2 - t1;
		System.out.println("logger(exception):" + t + ", speed:" + (1000.0 * count / t));
		
		t1 = System.currentTimeMillis();
		for(int i = 0; i < count; i++){
			logger4.info("type(alarm)" + head + i);
		}
		t2 = System.currentTimeMillis();
		t = t2 - t1;
		System.out.println("logger(alarm):" + t + ", speed:" + (1000.0 * count / t));
		
		t1 = System.currentTimeMillis();
		for(int i = 0; i < count; i++){
			logger5.info("type(others)" + head + i);
		}
		t2 = System.currentTimeMillis();
		t = t2 - t1;
		System.out.println("logger(others):" + t + ", speed:" + (1000.0 * count / t));
		
		t1 = System.currentTimeMillis();
		for(int i = 0; i < count; i++){
			logger6.info("type(debug)" + head + i);
		}
		t2 = System.currentTimeMillis();
		t = t2 - t1;
		System.out.println("logger(debug):" + t + ", speed:" + (1000.0 * count / t));
	}
	*/
}