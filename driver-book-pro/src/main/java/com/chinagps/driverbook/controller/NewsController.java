package com.chinagps.driverbook.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinagps.driverbook.pojo.News;
import com.chinagps.driverbook.pojo.NewsRecord;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.INewsService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 资讯Controller
 * @author Ben
 *
 */

@Controller("newsController")
@RequestMapping(value="/news")
public class NewsController {
	private static Logger logger = LoggerFactory.getLogger(NewsController.class);
	
	@Autowired
	@Qualifier("newsServiceImpl")
	private INewsService newsService;
	
	/**
	 * 海马资讯列表接口
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping
	@ResponseBody
	public String index(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			HashMap<String, String> params = RequestUtil.getParameters(encryptStr, new TypeReference<HashMap<String, String>>() {});
			rv = newsService.listNews(params, rv);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 海马资讯详情页面
	 * @param id 资讯ID
	 * @param customerId 客户ID
	 * @param device 设备类型
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable Long id, Long customerId, Integer device, Model model) {
		try {
			News news = newsService.findById(id);
			model.addAttribute("news", news);
			// 日志放在查询后面，如果日志记录失败资讯依然可读
			if (customerId != null && device != null) {
				NewsRecord newsRecord = new NewsRecord(id, customerId, device);
				newsService.addRecord(newsRecord);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "news/show";
	}
	
}
