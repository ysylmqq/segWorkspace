package com.chinagps.driverbook.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinagps.driverbook.pojo.BarSeries;
import com.chinagps.driverbook.service.ISigninReportService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/admin/signinreports")
public class SigninReportController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SigninReportController.class);
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	@Qualifier("signinReportServiceImpl")
	private ISigninReportService signinReportService;
	
	@RequestMapping
	public String index(String beginTime, String endTime, Model model) {
		try {
			List<String> categories = signinReportService.categories(beginTime, endTime);
			List<Integer> androidData = signinReportService.loginInfo(beginTime, endTime, 0);
			BarSeries androidSeries = new BarSeries();
			androidSeries.setName("Android");
			androidSeries.setData(androidData);
			List<Integer> iosData = signinReportService.loginInfo(beginTime, endTime, 1);
			BarSeries iosSeries = new BarSeries();
			iosSeries.setName("iOS");
			iosSeries.setData(iosData);
			List<BarSeries> barSeries = new ArrayList<BarSeries>();
			barSeries.add(androidSeries);
			barSeries.add(iosSeries);
			model.addAttribute("beginTime", beginTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("categories", mapper.writeValueAsString(categories));
			model.addAttribute("barSeries", mapper.writeValueAsString(barSeries));
			return "/admin/signinreport/index";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
}
