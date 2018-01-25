package com.gboss.comm;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public final class LogManager {
	
	public static final void init() {
		String filePath = LogManager.class.getClassLoader().getResource("log4j.properties").getPath();
		PropertyConfigurator.configure(filePath);
	}
	
	static{
		init();
	}
	
	public static final Logger getLogger(String loggerName){
		Logger logger = Logger.getLogger(SystemConst.LOGGER_NAME_OTHERS);
		if(SystemConst.LOGGER_NAME_EXCEPTION.equals(loggerName)){
			 logger = Logger.getLogger(SystemConst.LOGGER_NAME_EXCEPTION);
		}else if(SystemConst.LOGGER_NAME_DEBUG.equals(loggerName)){
			 logger = Logger.getLogger(SystemConst.LOGGER_NAME_DEBUG);
		}else if(SystemConst.LOGGER_NAME_SYNCDATA.equals(loggerName)){
			logger = Logger.getLogger(SystemConst.LOGGER_NAME_SYNCDATA);
		}
		return logger;
	}

	public static void main(String[] args) {
		
		
	    Logger logger3 = LogManager.getLogger("nameException");
	    Logger logger5 = LogManager.getLogger("nameOthers");
	    Logger logger6 = LogManager.getLogger("nameDebug");
	    
		int count = 1000;
	    String head = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    long t1 = System.currentTimeMillis();
	    for (int i = 0; i < count; i++) {
//	    	logger.debug("type(default)" + head + i);
		}
	    long t2 = System.currentTimeMillis();
	    long t = t2 - t1;
	    System.out.println("logger(default):" + t + ", speed:" + 1000.0D * count / t);
	    
	    t1 = System.currentTimeMillis();
	    for (int i = 0; i < count; i++) {
	      logger3.warn("type(exception)" + head + i);
	    }
	    t2 = System.currentTimeMillis();
	    t = t2 - t1;
	    System.out.println("logger(exception):" + t + ", speed:" + 1000.0D * count / t);
	    t1 = System.currentTimeMillis();
	    for (int i = 0; i < count; i++) {
	      logger5.info("type(others)" + head + i);
	    }
	    t2 = System.currentTimeMillis();
	    t = t2 - t1;
	    System.out.println("logger(others):" + t + ", speed:" + 1000.0D * count / t);
	    t1 = System.currentTimeMillis();
	    for (int i = 0; i < count; i++) {
	      logger6.info("type(debug)" + head + i);
	    }
	    t2 = System.currentTimeMillis();
	    t = t2 - t1;
	    System.out.println("logger(debug):" + t + ", speed:" + 1000.0D * count / t);
	}
}
