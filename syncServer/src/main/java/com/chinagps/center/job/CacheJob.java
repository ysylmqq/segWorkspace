package com.chinagps.center.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.chinagps.center.service.impl.DataService;
import com.chinagps.center.utils.SpringContext;

/**
 *
 */
public class CacheJob {
	@Autowired
	@Qualifier("DataService")
	private DataService dataService;
	
	private static Logger logger = Logger.getLogger(CacheJob.class);
	public void doJob(){
		try {
			if(dataService ==null){
				dataService = (DataService) SpringContext.getBean("DataService");
			}
			dataService.initCache();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("cache job error :"+e.getMessage());
		}
	}
}
