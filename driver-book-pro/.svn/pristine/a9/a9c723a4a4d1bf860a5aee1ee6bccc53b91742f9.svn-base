package com.chinagps.driverbook.scheduler;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import com.chinagps.driverbook.dao.OilPriceMapper;
import com.chinagps.driverbook.pojo.OilPrice;

/**
 * 每日油价爬虫
 * 
 * @author Ben
 * 
 */
@Component
public class OilPriceProcessor implements PageProcessor {
	private static Logger logger = Logger.getLogger(OilPriceProcessor.class);
	
	@Autowired
	private OilPriceMapper oilPriceMapper;

	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	
	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		logger.debug("########开始爬取最新油价########");
		
		Selectable selectable = page.getHtml().css(".oilTable");
		List<String> provinceList = selectable.xpath("//tbody/tr/th/a/text()").all();
		List<String> priceList = selectable.xpath("//tbody/tr/td/text()").all();
		try {
			Pattern pattern = Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,2}$");
			for (int i = 0; i < provinceList.size(); i++) {
				OilPrice oilPrice = new OilPrice();
				String price90 = priceList.get(i * 4);
				String price93 = priceList.get(i * 4 + 1);
				String price97 = priceList.get(i * 4 + 2);
				// 利用正则表达式去除特殊字符
				oilPrice.setProvince(provinceList.get(i));
				if (pattern.matcher(price90).find()) {
					oilPrice.setPrice90(Double.parseDouble(price90));
				} else {
					oilPrice.setPrice90(Double.parseDouble(price90.substring(0, price90.indexOf("("))));
				}
				if (pattern.matcher(price93).find()) {
					oilPrice.setPrice93(Double.parseDouble(price93));
				} else {
					oilPrice.setPrice93(Double.parseDouble(price93.substring(0, price93.indexOf("("))));
				}
				if (pattern.matcher(price97).find()) {
					oilPrice.setPrice97(Double.parseDouble(price97));
				} else {
					oilPrice.setPrice97(Double.parseDouble(price97.substring(0, price97.indexOf("("))));
				}
				oilPrice.setPrice0(Double.parseDouble(priceList.get(i * 4 + 3)));

				OilPrice op = oilPriceMapper.findByProvince(oilPrice.getProvince());

				if (op ==null || !op.getPrice90().equals(oilPrice.getPrice90())
						|| !op.getPrice93().equals(oilPrice.getPrice93())
						|| !op.getPrice97().equals(oilPrice.getPrice97())
						|| !op.getPrice0().equals(oilPrice.getPrice0())) {
					if (oilPriceMapper.add(oilPrice) != 1) {
						logger.error("未保存的数据：" + oilPrice.getProvince() + "->" + oilPrice.getPrice90() + "|" + oilPrice.getPrice93() + "|" + oilPrice.getPrice97() + "|" + oilPrice.getPrice0());
					}
				}
			}
			logger.debug("########最新油价爬取完毕########");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
