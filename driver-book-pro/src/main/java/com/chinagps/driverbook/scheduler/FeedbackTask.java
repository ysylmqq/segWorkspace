package com.chinagps.driverbook.scheduler;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chinagps.driverbook.dao.FeedbackMapper;
import com.chinagps.driverbook.pojo.Feedback;
import com.chinagps.driverbook.service.IPushService;

/**
 * 意见反馈推送任务，每5分钟扫描一次意见反馈表，如果坐席回复了意见反馈就推送消息到手机APP
 * @author Ben
 *
 */
@Component
public class FeedbackTask {
	private static final Logger logger = LoggerFactory.getLogger(FeedbackTask.class);

	@Autowired
	private FeedbackMapper feedbackMapper;
	@Autowired
	private IPushService pushService;
	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void pushFeedbackResponse() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		try {
			List<Feedback> feedbackList = feedbackMapper.findForFeedbackTask();
			for (Feedback feedback : feedbackList) {
				sb.append(feedback.getCustomerId()).append(",");
				feedbackMapper.updateStatus(feedback.getId());
			}
			
			if (sb.length() > 0) {
				params.put("customerIds", sb.substring(0, sb.length() -1));
				params.put("origin", 2);
				params.put("title", "海马");
				params.put("content", "您的意见反馈已被回复，请点击查看");
				params.put("type", 3);
				params.put("uri", "content://feedback");
				pushService.push(params);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}
