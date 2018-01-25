package bhz.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bhz.com.constant.SystemConst;
import bhz.com.util.CreateExcel_PDF_CSV;
import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.com.util.ResultJson;
import bhz.com.util.Utils;
import bhz.sys.entity.Alarm;
import bhz.sys.entity.Fault;
import bhz.sys.facade.AlarmService;
import bhz.sys.facade.FaultService;



@Controller
@RequestMapping(value="/alarm")
public class AlarmController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AlarmController.class);

	private static final int Vehicle = 0;
	
	@Autowired
	private AlarmService alarmService;
	
	@Autowired
	private FaultService faultService;

	@RequestMapping(value = "/getAlarms.page", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVehicles( HttpServletRequest request) {
		PageSelect<Alarm> pageSelect = (PageSelect<Alarm>) request.getAttribute("pageSelect");
		Page<HashMap<String, Object>> result = null;
		try {
			result = alarmService.search(pageSelect, 201L);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return ResultJson.converToMap(result);
	}
	
	
	@RequestMapping(value = "exportAlarmInfos")
	public void exportArrearageInfos(HttpServletRequest request,HttpServletResponse response){
		try {
			String[][] title = {
					{"序号","10"},{"客户名称","20"},{"车牌号码","14"},{"呼号","14"},
					{"定位时间","20"},{"是否定位","5"},{"纬度","15"},{"经度","15"},
					{"速度","10"},{"方向","5"},{"总里程","15"},{"警情状态","15"},
			};
			//条件
			Map map=parseReqParam2(request);
			List<Map<String, Object>> results = alarmService.findAllAlarms(map);
			List valueList = new ArrayList();
			Map<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			int columnIndex=0;
			
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex] = Utils.clearNull(valueData.get("customer_name"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("plate_no"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("call_letter"))+" ";
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("gps_time"));
				columnIndex++;
				values[columnIndex]  = "1".equals((String)Utils.clearNull(valueData.get("is_location"))) ? "是":"否";
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("lat"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("lng"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("speed"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("course"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("total_distance"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("status"));
				valueList.add(values);
			}
			//获得分公司中英文名称
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "警情信息报表", title,"test","test2",false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping(value = "/getFaults.page", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getFaults( HttpServletRequest request) {
		PageSelect<Fault> pageSelect = (PageSelect<Fault>) request.getAttribute("pageSelect");
		String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
		Long subco=null;
		Page<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			subco = 201L;
			result = faultService.search(pageSelect, subco);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return ResultJson.converToMap(result);
	}
	
	@RequestMapping(value = "exportFaultInfos")
	public void exportFaultInfos(HttpServletRequest request,HttpServletResponse response){
		try {
			String[][] title = {
					{"序号","10"},{"呼号","14"},
					{"故障码","20"},{"故障类型名","30"},{"故障名","30"},{"定位时间","20"}
			};
			//条件
			Map map=parseReqParam2(request);
			map.put("subcoNo", 201);

			List<Map<String, Object>> results = faultService.findAllAlarms(map);
			List valueList = new ArrayList();
			Map<String, Object> valueData = null;
			String[] values = null;
			int listLenth=results.size();
			int titleLength=title.length;
			int columnIndex=0;
			
			for(int i=0; i<listLenth; i++){
				values = new String[titleLength];
				valueData = results.get(i);
				columnIndex=0;
				values[columnIndex]  = (i+1)+"";
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("call_letter"))+"";
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("faultcode"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("faulttypename"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("faultname"));
				columnIndex++;
				values[columnIndex]  = Utils.clearNull(valueData.get("starttime"));
				valueList.add(values);
			}
			//获得分公司中英文名称
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, "警情故障信息报表", title,"警情故障信息报表","",false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping(value = "/getFaultType", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getFaultType( HttpServletRequest request) {
		return faultService.getFalutCodeByName("故障");
	}

	/**
	 * 警情统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAlarmAccount", method = RequestMethod.GET)
	public @ResponseBody  List<Map<String, Object>> getAlarmAccount( HttpServletRequest request) {
		String start_date = request.getParameter("start_date");
		String end_date =  request.getParameter("end_date");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		return alarmService.alarmCount(map);
	}
	
	
	/**
	 * 故障统计
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getFaultAccount", method = RequestMethod.GET)
	public @ResponseBody  List<Map<String, Object>> getFaultAccount( HttpServletRequest request) {
		String start_date = request.getParameter("start_date");
		String end_date =  request.getParameter("end_date");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		return alarmService.faultCount(map);
	}
}

