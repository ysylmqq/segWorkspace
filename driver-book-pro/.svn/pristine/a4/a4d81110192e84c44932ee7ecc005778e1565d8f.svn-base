package com.chinagps.driverbook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.NoticeHis;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.INoticeHisService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.chinagps.driverbook.util.pagination.Page;

@RestController
@RequestMapping(value = "/notice_his")
public class NoticeHisController {
	private static Logger logger = LoggerFactory.getLogger(NoticeHisController.class);
	
	@Autowired
	private INoticeHisService noticeHisService;
	
	@RequestMapping(method = RequestMethod.POST)
	public String index(@RequestBody String encryptStr, ReturnValue rv) {
		try {
			NoticeHis noticeHis = RequestUtil.getParameters(encryptStr, NoticeHis.class);
			Integer pageNum = RequestUtil.getIntegerValue(encryptStr, "pageNum");
			Integer numPerPage = RequestUtil.getIntegerValue(encryptStr, "numPerPage");
			if (pageNum == null) {
				pageNum = 1;
			}
			if (numPerPage == null) {
				numPerPage = 20;
			}
			Page page = new Page();
			page.setPageNum(pageNum);
			page.setNumPerPage(numPerPage);
			int count = noticeHisService.countAll(noticeHis);
			page.setTotalCount(count);
			List<NoticeHis> hisList = noticeHisService.findByPage(noticeHis, new RowBounds(page.getOffset(), page.getNumPerPage()));
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("page", page);
			resultMap.put("his", hisList);
			rv.setDatas(resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ResponseUtil.encrypt(rv);
	}
	
	
}
