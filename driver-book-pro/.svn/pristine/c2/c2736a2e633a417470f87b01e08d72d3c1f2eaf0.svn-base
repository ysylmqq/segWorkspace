package com.chinagps.driverbook.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public final class LogManager {
	
	private final static String SYS_LOG 	= "nameSystem";
	private final static String D_LOG 		= "nameDebug";
	private final static String UB_LOG 		= "nameUnBind";
	private final static String PUSH_LOG 	= "namePush";
	private final static String E_LOG 		= "nameException";
	
	public static final void init() {
		String filePath = LogManager.class.getClassLoader().getResource("log4j.properties").getPath();
		PropertyConfigurator.configure(filePath);
	}
	
	static{
		init();
	}
	
	public static final Logger getLogger(String loggerName){
		Logger logger = Logger.getLogger(SYS_LOG);
		if(D_LOG.equals(loggerName)){
			 logger = Logger.getLogger(D_LOG);
		}else if(UB_LOG.equals(loggerName)){
			 logger = Logger.getLogger(UB_LOG);
		}else if(E_LOG.equals(loggerName)){
			 logger = Logger.getLogger(E_LOG);
		}else if(PUSH_LOG.equals(loggerName)){
			logger = Logger.getLogger(PUSH_LOG);
		}
		return logger;
	}

	public static void main(String[] args) {
		
		
	    Logger logger3 = LogManager.getLogger("nameException");
	    Logger logger5 = LogManager.getLogger("namePush");
	    Logger logger6 = LogManager.getLogger("nameSystem");
	    Logger logger7 = LogManager.getLogger("nameUnBind");
	    Logger logger8 = LogManager.getLogger("nameDebug");
	    
		int count = 1000;
	    String head = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    long t1 = System.currentTimeMillis();
	    for (int i = 0; i < count; i++) {
	    	logger7.debug("type(nameUnBind)" + head + i);
		}
	    long t2 = System.currentTimeMillis();
	    long t = t2 - t1;
	    System.out.println("logger(nameUnBind):" + t + ", speed:" + 1000.0D * count / t);
	    
	    t1 = System.currentTimeMillis();
	    for (int i = 0; i < count; i++) {
	      logger3.warn("type(exception)" + head + i);
	    }
	    t2 = System.currentTimeMillis();
	    t = t2 - t1;
	    System.out.println("logger(exception):" + t + ", speed:" + 1000.0D * count / t);
	    
	    t1 = System.currentTimeMillis();
	    for (int i = 0; i < count; i++) {
	      logger8.warn("type(nameDebug)" + head + i);
	    }
	    t2 = System.currentTimeMillis();
	    t = t2 - t1;
	    System.out.println("logger(nameDebug):" + t + ", speed:" + 1000.0D * count / t);
	    
	    t1 = System.currentTimeMillis();
	    for (int i = 0; i < count; i++) {
	      logger5.info("type(namePush)" + head + i);
	    }
	    t2 = System.currentTimeMillis();
	    t = t2 - t1;
	    System.out.println("logger(namePush):" + t + ", speed:" + 1000.0D * count / t);
	    t1 = System.currentTimeMillis();
	    for (int i = 0; i < count; i++) {
	      logger6.info("type(nameSystem)" + head + i);
	    }
	    t2 = System.currentTimeMillis();
	    t = t2 - t1;
	    System.out.println("logger(nameSystem):" + t + ", speed:" + 1000.0D * count / t);
	}
}
