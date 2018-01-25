package bhz.mst.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bhz.mst.facade.ChartDataService;


/**
 * @Package:com.gboss.controller
 * @ClassName:VehicleController
 * @Description:从门店copy过来，以后可能会改成静态数据（搜索引擎）
 * @author:bzhang
 * @date:2014-6-5 下午3:39:01
 */
@Controller
@RequestMapping(value="/chart")
public class ChartController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ChartController.class);

	@Autowired
	private ChartDataService chartDataService;
	
	@RequestMapping(value = "/getChartData/czxg", method = RequestMethod.POST)
	public @ResponseBody Object czxg(
			@RequestParam(value="year",required=true) int year,
			@RequestParam(value="month",required=true) int month,
			@RequestParam(value="type",required=true) int type) {
		List<Map<String, Object>> list1 = chartDataService.getChartData(year, month, 1);
		List<Map<String, Object>> list2 = chartDataService.getChartData(year, month, 6);
		List<Object> res = new ArrayList<Object>();
		res.add(list1);
		res.add(list2);
		return res;
	}
	
	@RequestMapping(value = "/getChartData/xbfb", method = RequestMethod.POST)
	public @ResponseBody Object xbfb(
			@RequestParam(value="year",required=true) int year,
			@RequestParam(value="month",required=true) int month,
			@RequestParam(value="type",required=true) int type) {
		List<Map<String, Object>> list1 = chartDataService.getChartData(year, month, 3);
		List<Map<String, Object>> list2 = chartDataService.getChartData(year, month, 4);
		List<Object> res = new ArrayList<Object>();
		res.add(list1);
		res.add(list2);
		return res;
	}
	
	@RequestMapping(value = "/getChartData/ycxg", method = RequestMethod.POST)
	public @ResponseBody Object ycxg(
			@RequestParam(value="year",required=true) int year,
			@RequestParam(value="month",required=true) int month,
			@RequestParam(value="type",required=true) int type) {
		List<Map<String, Object>> list1 = chartDataService.getChartData(year, month, 8);
		List<Map<String, Object>> list2 = chartDataService.getChartData(year, month, 9);
		List<Map<String, Object>> list3 = chartDataService.getChartData(year, month, 12);
		List<Map<String, Object>> list4 = chartDataService.getChartData(year, month, 10);
		List<Map<String, Object>> list5 = chartDataService.getChartData(year, month, 11);
		List<Object> res = new ArrayList<Object>();
		res.add(list1);
		res.add(list2);
		res.add(list3);
		res.add(list4);
		res.add(list5);
		return res;
	}
	
	@RequestMapping(value = "/getChartData", method = RequestMethod.POST)
	public @ResponseBody Object getChartData(
			@RequestParam(value="year",required=true) int year,
			@RequestParam(value="month",required=true) int month,
			@RequestParam(value="type",required=true) int type) {
		return chartDataService.getChartData(year, month, type);
	}
	
}

