package com.chinagps.driverbook.controller.admin;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinagps.driverbook.service.IActiveReportService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/admin/activereports")
public class ActiveReportController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ActiveReportController.class);
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	@Qualifier("activeReportServiceImpl")
	private IActiveReportService activeReportService;
	
	@RequestMapping
	public String index(String days, String times, Model model) {
		try {
			if (days == null || "".equals(days)) {
				days = "7";
			}
			if (times == null || "".equals(times)) {
				times = "3";
			}
			List<Map<String, Integer>> dataList = activeReportService.timesInPastDays(Integer.parseInt(days), Integer.parseInt(times));
			model.addAttribute("days", days);
			model.addAttribute("times", times);
			model.addAttribute("pieSeries", mapper.writeValueAsString(dataList));
			return "/admin/activereport/index";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
}
