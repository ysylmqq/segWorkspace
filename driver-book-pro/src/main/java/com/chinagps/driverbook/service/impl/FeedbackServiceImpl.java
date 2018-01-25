package com.chinagps.driverbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.FeedbackMapper;
import com.chinagps.driverbook.pojo.Feedback;
import com.chinagps.driverbook.service.IFeedbackService;

@Service
@Scope("prototype")
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback> implements IFeedbackService {
	
	@Autowired
	private FeedbackMapper feedbackMapper;

}
