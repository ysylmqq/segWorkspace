package com.chinagps.driverbook.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.Feedback;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.IFeedbackService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.chinagps.driverbook.util.pagination.Page;

/**
 * 意见反馈Controller
 * @author Ben
 *
 */
@RestController("feedbackController")
@RequestMapping(value="/feedback")
public class FeedbackController {
	private static Logger logger = LoggerFactory.getLogger(FeedbackController.class);
	
	@Autowired
	@Qualifier("feedbackServiceImpl")
	private IFeedbackService feedbackService;
	
	/**
	 * 意见反馈列表
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping
	public String index(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Integer pageNum = RequestUtil.getIntegerValue(encryptStr, "pageNum");
			Integer numPerPage = RequestUtil.getIntegerValue(encryptStr, "numPerPage");
			Long customerId = RequestUtil.getLongValue(encryptStr, "customerId");
			Page page = new Page();
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			if (pageNum == null) {
				page.setPageNum(1);
			} else {
				page.setPageNum(pageNum);
			}
			if (numPerPage == null) {
				page.setNumPerPage(20);
			} else {
				page.setNumPerPage(numPerPage);
			}
			Feedback feedback = new Feedback();
			feedback.setCustomerId(customerId);
			int count = feedbackService.countAll(feedback);
			page.setTotalCount(count);
			List<Feedback> feedbackList = feedbackService.findByPage(feedback, new RowBounds(page.getOffset(), page.getNumPerPage()));
			resultMap.put("page", page);
			resultMap.put("feedback", feedbackList);
			rv.setDatas(resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
	/**
	 * 新增反馈
	 * @param encryptStr 参数密文
	 * @param rv ReturnValue实例
	 * @return ReturnValue返回值实例
	 */
	@RequestMapping(value="/new")
	public String _new(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			Feedback feedback = RequestUtil.getParameters(encryptStr, Feedback.class);
			feedbackService.add(feedback);
			rv.setDatas(feedback.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rv.systemError();
		}
		return ResponseUtil.encrypt(rv);
	}
	
}
