package com.chinagps.driverbook.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;

@Component
public class OilPriceTask {
	@Autowired
	private OilPriceProcessor oilPriceProcessor;
	
	@Scheduled(cron="0 30 7 * * ?")
	public void runSpider() {
		Spider.create(oilPriceProcessor).addUrl("http://www.bitauto.com/youjia/").run();
	}
}
